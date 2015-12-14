package com.coding.android.easyfastcoding.demo.pull2refresh.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.coding.android.easyfastcoding.R;
import com.coding.android.easyfastcoding.datafactory.ListDate;
import com.coding.android.easyfastcoding.datafactory.model.QuickBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import easyfastcode.library.widget.adapter.QuickAdapter;
import easyfastcode.library.widget.adapter.base.BaseAdapterHelper;

/**
 * Created by cy on 2015/12/14.
 */
public class ListViewRefreshFragment extends Fragment {

    @Bind(R.id.lv)
    ListView lv;

    public static ListViewRefreshFragment initFragment() {
        return new ListViewRefreshFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View center = inflater.inflate(R.layout.fragment_list_refresh, null);
        ButterKnife.bind(this, center);
        initView();
        return center;
    }

    private void initView() {
        lv.setAdapter(new QuickAdapter<QuickBean>(getContext(), R.layout.item_quick_adapter_test, ListDate.getQuickBean(10)) {
            @Override
            public View createNoDataView() {
                return null;
            }

            @Override
            protected void convert(BaseAdapterHelper helper, QuickBean item) {
                helper.setText(R.id.tv_quick_adpeter, item.getInfo());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
