package com.coding.android.easyfastcoding.widget.pull2refresh;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coding.android.easyfastcoding.R;

import org.w3c.dom.Text;

/**
 * Created by cy on 2015/11/20.
 * <p/>
 * 继承改抽象类实现相应的抽象方法，做出各种下拉刷新，上拉加载效果
 */
public abstract class BaseRefreshViewHolder {
    /**
     * 是否开启加载更多功能
     */
    private boolean mIsLoadingMoreEnabled = true;
    /**
     * 整个加载更多控件的背景颜色资源id
     */
    private int mLoadingMoreBgColorRes = -1;
    /**
     * 整个加载更多控件的背景drawable资源id
     */
    private int mLoadingMoreBgDrawableRes = -1;

    /**
     * 头部控件移动动画时常
     */
    private int mTopAnimDuration = 500;

    /**
     * 下拉刷新控件的背景颜色id
     */
    protected int mRefreshBgColorRes = -1;
    /**
     * 下拉刷新控件的背景图片id
     */
    protected int mRefreshBgDrawableRes = -1;

    /**
     * 上拉加载更多View
     */
    protected View mLoadingMoreView;
    /**
     * 下拉刷新View
     */
    protected View mRefreshView;

    /**
     * 加载更多布局Textview
     */
    protected TextView mFooterStatusTv;
    /**
     * 底部加载更多菊花ImageView控件
     */
    protected ImageView mFooterChrysanthemumIv;
    /**
     * 底部加载更多菊花AnimationDrawable
     */
    protected AnimationDrawable mFooterChrysanthemumAd;


    protected Context mContext;
    /**
     * 正在加载更多时的文本
     */
    protected String mLoadingMoreText = "加载中...";


    /**
     * @param context
     * @param isLoadingMoreEnabled 上拉加载更多是否可用
     */
    public BaseRefreshViewHolder(Context context, boolean isLoadingMoreEnabled) {
        mContext = context;
        mIsLoadingMoreEnabled = isLoadingMoreEnabled;
    }

    //=================================== 加载更多 ===================================

    /**
     * 设置加载更多的文字提示
     */
    public void setLoadingMoreText(String loadingMoreText) {
        mLoadingMoreText = loadingMoreText;
    }

    /**
     * 设置整个加载更多控件的背景颜色资源id
     */
    public void setLoadingMoreBgColorRes(@ColorRes int loadingMoreBgColorRes) {
        mLoadingMoreBgColorRes = loadingMoreBgColorRes;
    }

    /**
     * 设置整个加载更多控件的背景图片资源id
     */
    public void setmLoadingMoreBgDrawableRes(@DrawableRes int loadingMoreBgDrawableRes) {
        mLoadingMoreBgDrawableRes = loadingMoreBgDrawableRes;
    }

    /**
     * 获取上拉加载更多控件，如果不喜欢这种上拉刷新风格可重写该方法实现自定义LoadMoreFooterView
     */
    public View getLoadingMoreView() {
        if (mIsLoadingMoreEnabled) {
            return null;
        }
        if (mLoadingMoreView == null) {
            mLoadingMoreView = View.inflate(mContext, R.layout.ef_view_refresh_footer, null);
            mLoadingMoreView.setBackgroundColor(Color.TRANSPARENT);
            if (mLoadingMoreBgColorRes != -1) {
                mLoadingMoreView.setBackgroundResource(mLoadingMoreBgColorRes);
            }
            if (mLoadingMoreBgDrawableRes != -1) {
                mLoadingMoreView.setBackgroundResource(mLoadingMoreBgDrawableRes);
            }

            mFooterStatusTv = (TextView) mLoadingMoreView.findViewById(R.id.tv_normal_refresh_footer_status);
            mFooterChrysanthemumIv = (ImageView) mLoadingMoreView.findViewById(R.id.iv_normal_refresh_footer_chrysanthemum);
            mFooterChrysanthemumAd = (AnimationDrawable) mFooterChrysanthemumIv.getDrawable();
            mFooterStatusTv.setText(mLoadingMoreText);
        }
        return mLoadingMoreView;
    }

    //=================================== 下拉刷新 ====================================

    /**
     * 设置下拉刷新背景颜色id
     */
    public void setRefreshBgColorRes(@ColorRes int refreshBgColorRes) {
        mRefreshBgColorRes = refreshBgColorRes;
    }

    /**
     * 设置下拉刷新背景图片id
     */
    public void setRefreshBgDrawableRes(@DrawableRes int refreshBgDrawableRes) {
        mRefreshBgDrawableRes = refreshBgDrawableRes;
    }

    /**
     * 设置顶部未满足下拉刷新条件时回弹到初始状态、满足刷新条件时回弹到正在刷新状态、刷新完毕后回弹到初始状态的动画时间，默认为500毫秒
     */
    public int getTopAnimDuration() {
        return mTopAnimDuration;
    }

    /**
     * 设置顶部未满足下拉刷新条件时回弹到初始状态、满足刷新条件时回弹到正在刷新状态、刷新完毕后回弹到初始状态的动画时间，默认为500毫秒
     */
    public void setmTopAnimDuration(int topAnimDuration) {
        mTopAnimDuration = topAnimDuration;
    }

    //===================================== 需要子类实现的抽象方法 ====================================

    /**
     * 获取头部下拉刷新控件，由子类实现
     */
    public abstract View getRefreshView();

    /**
     * 下拉刷新控件可见时，处理上下拉进度
     *
     * @param scale         下拉过程0 到 1，回弹过程1 到 0，没有加上弹簧距离移动时的比例
     * @param moveYDistance 整个下拉刷新控件paddingTop变化的值，如果有弹簧距离，会大于整个下拉刷新控件的高度
     */
    public abstract void handleScale(float scale, int moveYDistance);
    /**
     * 进入到未处理下拉刷新状态
     */
    public abstract void changeToIdle();

    /**
     * 进入下拉状态
     */
    public abstract void changeToPullDown();

    /**
     * 进入释放刷新状态
     */
    public abstract void changeToReleaseRefresh();

    /**
     * 进入正在刷新状态
     */
    public abstract void changeToRefreshing();

    /**
     * 结束下拉刷新
     */
    public abstract void onEndRefreshing();

}
