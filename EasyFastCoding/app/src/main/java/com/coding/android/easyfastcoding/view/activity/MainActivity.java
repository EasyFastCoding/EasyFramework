package com.coding.android.easyfastcoding.view.activity;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.coding.android.easyfastcoding.R;
import com.coding.android.easyfastcoding.view.model.DemoItemModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import easyfastcode.library.utils.UI.DensityUtils;
import easyfastcode.library.utils.other.AssetsUtils;
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
        parseJson();
    }

    private void parseJson() {
        String jsonString = AssetsUtils.getContent(this, "demos");
        demos = getDemoList(jsonString, 0);

        rv.setAdapter(new QuickRecycleViewAdapter<DemoItemModel>(R.layout.item_quick_adapter_test, demos) {
            @Override
            protected void onBindData(Context context, int position, final DemoItemModel item, int itemLayoutId, ViewHelper helper) {
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

                                } else {

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
        Type listType = new TypeToken<List<DemoItemModel>>() {
        }.getType();
        Gson gson = new Gson();
        items = gson.fromJson(jsonString, listType);
        for (int i = 0; i < items.size(); i++) {
            items.get(i).depth = depth;
        }
        return items;
    }
}
