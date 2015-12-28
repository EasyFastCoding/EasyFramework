package com.coding.android.easyfastcoding.demo.pull2refresh.fragment;

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
import easyfastcode.library.widget.adapter.listview.QuickAdapter;
import easyfastcode.library.widget.adapter.listview.BaseAdapterHelper;
import easyfastcode.library.widget.pull2refresh.base.BaseRefreshLayout;
import easyfastcode.library.widget.pull2refresh.custom.AnimationRefreshViewHolder;

/**
 * Created by cy on 2015/12/15.
 */
public class AnimationRefreshFragment extends Fragment implements BaseRefreshLayout.RefreshLayoutDelegate {

    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.refresh_layout)
    BaseRefreshLayout refreshLayout;

    public static AnimationRefreshFragment initFragment() {
        return new AnimationRefreshFragment();
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

        AnimationRefreshViewHolder meituanRefreshViewHolder = new AnimationRefreshViewHolder(getContext(), true);
        meituanRefreshViewHolder.setPullDownImageResource(R.mipmap.meituan_refresh_mt_pull_down);
        meituanRefreshViewHolder.setChangeToReleaseRefreshAnimResId(R.anim.meituan_refresh_mt_change_to_release_refresh);
        meituanRefreshViewHolder.setRefreshingAnimResId(R.anim.meituan_refresh_mt_refreshing);

        refreshLayout.setRefreshViewHolder(meituanRefreshViewHolder);

        lv.setAdapter(new QuickAdapter<QuickBean>(getContext(), R.layout.item_quick_adapter_test, ListDate.getQuickBean(30)) {
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

    @Override
    public void onRefreshLayoutBeginRefreshing(BaseRefreshLayout baseRefreshLayout) {
        mHandler.sendEmptyMessageDelayed(ON_REFRESH, 2000);
    }

    @Override
    public boolean onRefreshLayoutBeginLoadingMore(BaseRefreshLayout baseRefreshLayout) {
        mHandler.sendEmptyMessageDelayed(ON_LOADMORE, 2000);
        return true;
    }

    private final int ON_REFRESH = 1;
    private final int ON_LOADMORE = 2;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
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
