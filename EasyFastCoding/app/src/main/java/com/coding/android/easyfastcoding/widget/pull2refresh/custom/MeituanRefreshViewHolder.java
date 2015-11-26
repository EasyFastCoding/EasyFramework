package com.coding.android.easyfastcoding.widget.pull2refresh.custom;

import android.content.Context;
import android.view.View;

import com.coding.android.easyfastcoding.widget.pull2refresh.base.BaseRefreshViewHolder;

/**
 * Created by cy on 2015/11/26.
 */
public class MeituanRefreshViewHolder extends BaseRefreshViewHolder {
    /**
     * @param context
     * @param isLoadingMoreEnabled 上拉加载更多是否可用
     */
    public MeituanRefreshViewHolder(Context context, boolean isLoadingMoreEnabled) {
        super(context, isLoadingMoreEnabled);
    }

    @Override
    public View getRefreshView() {
        return null;
    }

    @Override
    public void handleScale(float scale, int moveYDistance) {

    }

    @Override
    public void changeToIdle() {

    }

    @Override
    public void changeToPullDown() {

    }

    @Override
    public void changeToReleaseRefresh() {

    }

    @Override
    public void changeToRefreshing() {

    }

    @Override
    public void onEndRefreshing() {

    }
}
