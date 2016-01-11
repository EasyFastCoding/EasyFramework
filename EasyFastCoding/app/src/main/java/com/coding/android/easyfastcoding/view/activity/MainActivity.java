package com.coding.android.easyfastcoding.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.coding.android.easyfastcoding.R;
import com.coding.android.easyfastcoding.view.model.DemoItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import easyfastcode.library.utils.UI.DensityUtils;
import easyfastcode.library.utils.other.AssetsUtils;
import easyfastcode.library.widget.adapter.ISelectable;
import easyfastcode.library.widget.adapter.QuickRecycleViewAdapter;
import easyfastcode.library.widget.adapter.ViewHelper;

/**
 * Created by cy on 2016/1/4.
 */
public class MainActivity extends BaseActivity {

    @Bind(R.id.rv)
    RecyclerView rv;

    private List<DemoItemModel> demos;

    private int itemMargin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        itemMargin = DensityUtils.dp2px(this, 10);
        rv.setLayoutManager(new LinearLayoutManager(this));
        showDemoList();
    }

    private void showDemoList() {
        String jsonString = AssetsUtils.getContent(this, "demos.json");
        demos = getDemoList(jsonString, 0);

        rv.setAdapter(new QuickRecycleViewAdapter<DemoItemModel>(R.layout.item_quick_adapter_test, demos, ISelectable.SELECT_MODE_MULTI) {
            @Override
            protected void onBindData(Context context, final int position, final DemoItemModel item, int itemLayoutId, ViewHelper helper) {
                switch (itemLayoutId) {
                    case R.layout.item_recycler_parent:
                        ImageView expand = helper.getView(R.id.expand);
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) expand.getLayoutParams();
                        params.leftMargin = itemMargin * item.depth;
                        expand.setLayoutParams(params);

                        helper.setText(R.id.text, item.name);
                        helper.setText(R.id.count, item.info);

                        if (item.isSelected()) {
                            expand.setRotation(90);
                        } else {
                            expand.setRotation(0);
                        }

                        helper.getView(R.id.container).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (item.isSelected()) {
                                    item.setSelected(false);
                                    ((QuickRecycleViewAdapter) rv.getAdapter()).getAdapterManager().removeItem(position + 1);
                                } else {
                                    List<DemoItemModel> childDemo = getDemoList(item.content, item.depth + 1);
                                    if (childDemo.size() > 0) {
                                        item.childlength = childDemo.size();
                                        item.setSelected(true);

                                        ((QuickRecycleViewAdapter) rv.getAdapter()).getAdapterManager().addItems(position + 1, childDemo);
                                    }
                                }
                            }
                        });
                        break;
                    case R.layout.item_recycler_child:
                        ImageView image = helper.getView(R.id.image);
                        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) image.getLayoutParams();
                        params1.leftMargin = itemMargin * item.depth + itemMargin;
                        image.setLayoutParams(params1);

                        helper.setText(R.id.text, item.name);
                        helper.getView(R.id.container).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                        break;
                }
            }

            @Override
            protected int getItemLayoutId(int position, DemoItemModel demoItemModel) {
                switch (demoItemModel.type) {
                    case 1:
                        return R.layout.item_recycler_parent;
                    case 2:
                        return R.layout.item_recycler_child;
                }
                return 0;
            }
        });
    }

    private List<DemoItemModel> getDemoList(String jsonString, int depth) {
        List<DemoItemModel> items = new ArrayList<>();
        if (!TextUtils.isEmpty(jsonString)) {
            try {
                JSONArray demos = new JSONArray(jsonString);
                for (int i = 0; i < demos.length(); i++) {
                    JSONObject demo = demos.optJSONObject(i);
                    DemoItemModel model = new DemoItemModel();
                    model.name = demo.optString("name");
                    model.info = demo.optString("info");
                    model.type = demo.optInt("type");
                    model.depth = depth;
                    if (demo.has("content"))
                        model.content = demo.optJSONArray("content").toString();
                    items.add(model);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return items;
    }
}
