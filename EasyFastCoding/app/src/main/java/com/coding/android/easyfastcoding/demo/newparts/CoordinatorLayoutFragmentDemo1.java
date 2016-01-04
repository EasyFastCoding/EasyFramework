package com.coding.android.easyfastcoding.demo.newparts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.coding.android.easyfastcoding.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import easyfastcode.library.utils.ui.UIUtils;

/**
 * Created by 杨强彪 on 2015/12/18.
 *
 * @描述：
 */
public class CoordinatorLayoutFragmentDemo1 extends Fragment {
    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View center = inflater.inflate(R.layout.fragment_coordinator_demo1, null);
        ButterKnife.bind(this, center);
        initEvent();

        return center;
    }

    private void initEvent() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "CoordinatorLayout 和 FloatingActionButton 和Snackbar的使用", Snackbar.LENGTH_LONG)
                        .setAction("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //这里的单击事件代表点击消除Action后的响应事件
                                Toast.makeText(UIUtils.getContext(), "被点击了", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
