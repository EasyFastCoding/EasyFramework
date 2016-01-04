package com.coding.android.easyfastcoding.demo.pull2refresh.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import easyfastcode.library.widget.adapter.ViewHelper;
import easyfastcode.library.widget.dialog.Dialogue;
import easyfastcode.library.widget.pull2refresh.base.BaseRefreshLayout;
import easyfastcode.library.widget.pull2refresh.custom.NormalRefreshViewHolder;

/**
 * Created by cy on 2015/12/14.
 */
public class NormalRefreshFragment extends Fragment implements BaseRefreshLayout.RefreshLayoutDelegate {

    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.refresh_layout)
    BaseRefreshLayout refreshLayout;

    public static NormalRefreshFragment initFragment() {
        return new NormalRefreshFragment();
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
        refreshLayout.setDelegate(this);
        refreshLayout.setRefreshViewHolder(new NormalRefreshViewHolder(getContext(), true));

<<<<<<< HEAD
        lv.setAdapter(new QuickAdapter<QuickBean>( R.layout.item_quick_adapter_test, ListDate.getQuickBean(30)) {
=======
        lv.setAdapter(new QuickAdapter<QuickBean>(R.layout.item_quick_adapter_test, ListDate.getQuickBean(30)) {
>>>>>>> 357454ac9cca099b93c1d5266ca0beae5652001e
            @Override
            public View createNoDataView() {
                return null;
            }

            @Override
            protected void onBindData(Context context, int position, QuickBean item, int itemLayoutId, ViewHelper helper) {
                helper.setText(R.id.tv_quick_adpeter, item.getInfo());

            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefreshLayoutBeginRefreshing(BaseRefreshLayout baseRefreshLayout) {
        Dialogue.show(getContext());
        mHandler.sendEmptyMessageDelayed(ON_REFRESH, 2000);
    }

    @Override
    public boolean onRefreshLayoutBeginLoadingMore(BaseRefreshLayout baseRefreshLayout) {
        Dialogue.show(getContext());
        mHandler.sendEmptyMessageDelayed(ON_LOADMORE, 2000);
        return true;
    }

    private final int ON_REFRESH = 1;
    private final int ON_LOADMORE = 2;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Dialogue.dissmiss(getContext());
            super.handleMessage(msg);
            switch (msg.what) {
                case ON_REFRESH:
                    refreshLayout.endRefreshing();
                    break;
                case ON_LOADMORE:
                    refreshLayout.endLoadingMore();
                    break;
            }

        }
    };
}
