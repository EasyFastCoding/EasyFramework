package easyfastcode.library.widget.pull2refresh.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import easyfastcode.library.R;

/**
 * Created by cy on 2015/11/20.
 * <p>
 * 处理刷新view的类，主要功能：上拉，下拉刷新viwe的显示效果配置，拉动距离设置等等。
 * 继承这个抽象类实现相应的抽象方法，做出各种下拉刷新，上拉加载效果
 * <p>
 * 上拉加载设置
 * <br/>
 * <br/>
 * 1、BaseRefreshViewHolder中修改上拉加载默认配置的几个方法
 * <br/>
 * 1.1、setLoadingMoreText：设置加载更多view的提示文字。
 * <br/>
 * 1.2、setLoadingMoreBgColorRes：设置整个加载更多控件的背景颜色资源id
 * <br/>
 * 1.3、setmLoadingMoreBgDrawableRes：设置整个加载更多控件的背景图片资源id
 * <br/>
 * <br/>
 * 2.如果不喜欢默认的加载更多view可重写getLoadingMoreView
 * <p>
 * 下拉刷新设置
 * <br/>
 * <br/>
 * 1、BaseRefreshViewHolder中修改下拉加载默认配置的几个方法
 * <br/>
 * 1.1、setRefreshBgColorRes：设置下拉刷新背景颜色id
 * <br/>
 * 1.2、setRefreshBgDrawableRes：设置下拉刷新背景图片id
 * <br/>
 * 1.3、setmTopAnimDuration：刷新完毕后回弹到初始状态的动画时间，默认为500毫秒
 * <br/>
 * 1.4、setPullDistanceScale：设置手指移动距离与下拉刷新控件paddingTop移动距离的比值，默认1.8
 * <br/>
 * 1.5、setSpringDistanceScale：下拉刷新弹簧的距离和下拉刷新控件高度的比值
 * <p>
 * 抽象方法
 * <br/>
 * <br/>
 * 1、getRefreshView：获取头部下拉刷新控件
 * <br/>
 * 2、handleScale：下拉刷新控件可见时，处理上下拉进度
 * <br/>
 * 3、changeToIdle：进入到未处理下拉刷新状态
 * <br/>
 * 4、changeToPullDown：进入下拉状态
 * <br/>
 * 5、changeToReleaseRefresh：进入释放刷新状态
 * <br/>
 * 6、changeToRefreshing：进入正在刷新状态
 * <br/>
 * 7、onEndRefreshing：结束下拉刷新
 */


public abstract class BaseRefreshViewHolder {

    /**
     * 手指移动距离与下拉刷新控件paddingTop移动距离的比值
     */
    private static final float PULL_DISTANCE_SCALE = 1.8f;
    /**
     * 下拉刷新控件paddingTop的弹簧距离与下拉刷新控件高度的比值
     */
    private static final float SPRING_DISTANCE_SCALE = 0.4f;
    //=========> private <==========
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
     * 手指移动距离与下拉刷新控件paddingTop移动距离的比值，默认1.8f
     */
    private float mPullDistanceScale = PULL_DISTANCE_SCALE;
    /**
     * 下拉刷新控件paddingTop的弹簧距离与下拉刷新控件高度的比值，默认0.4f
     */
    private float mSpringDistanceScale = SPRING_DISTANCE_SCALE;


    //=========> protected <==========
    /**
     * 下拉刷新控件的背景颜色id
     */
    protected int mRefreshBgColorRes = -1;
    /**
     * 下拉刷新控件的背景图片id
     */
    protected int mRefreshBgDrawableRes = -1;
    /**
     * 下拉刷新上拉加载更多控件
     */
    protected BaseRefreshLayout mBaseRefreshLayout;
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

    /**
     * 进入上拉加载更多状态
     */
    public void startLoadingMoreDrawableAnimation() {
        if (mIsLoadingMoreEnabled && mFooterChrysanthemumAd != null) {
            mFooterChrysanthemumAd.start();
        }
    }

    /**
     * 结束上拉加载更多
     */
    public void stopLoadingMoreDrawableAnimation() {
        if (mIsLoadingMoreEnabled && mFooterChrysanthemumAd != null) {
            mFooterChrysanthemumAd.stop();
        }
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

    /**
     * 获取下拉刷新控件的高度，如果初始化时的高度和最后展开的最大高度不一致，需重写该方法返回最大高度
     *
     * @return
     */
    public int getRefreshHeaderViewHeight() {
        if (mRefreshView != null) {
            // 测量下拉刷新控件的高度
            mRefreshView.measure(0, 0);
            return mRefreshView.getMeasuredHeight();
        }
        return 0;
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

    //======================================== 一些属性的配置 ========================================

    /**
     * 手指移动距离与下拉刷新控件paddingTop移动距离的比值
     */
    public float getPaddingTopScale() {
        return mPullDistanceScale;
    }

    /**
     * 设置手指移动距离与下拉刷新控件paddingTop移动距离的比值
     */
    public void setPullDistanceScale(float pullDistanceScale) {
        mPullDistanceScale = pullDistanceScale;
    }

    /**
     * 下拉刷新控件paddingTop的弹簧距离与下拉刷新控件高度的比值
     */
    public float getSpringDistanceScale() {
        return mSpringDistanceScale;
    }

    /**
     * 设置下拉刷新控件paddingTop的弹簧距离与下拉刷新控件高度的比值，不能小于0，如果刷新控件比较高，建议将该值设置小一些
     */
    public void setSpringDistanceScale(float springDistanceScale) {
        if (springDistanceScale < 0) {
            throw new RuntimeException("下拉刷新控件paddingTop的弹簧距离与下拉刷新控件高度的比值springDistanceScale不能小于0");
        }
        mSpringDistanceScale = springDistanceScale;
    }

    /**
     * 是处于能够进入刷新状态
     */
    public boolean canChangeToRefreshingStatus() {
        return false;
    }

    /**
     * 改变整个下拉刷新头部控件移动一定的距离（带动画），自定义刷新控件进入刷新状态前后的高度有变化时可以使用该方法（参考BGAStickinessRefreshView）
     *
     * @param distance
     */
    public void startChangeWholeHeaderViewPaddingTop(int distance) {
        mBaseRefreshLayout.startChangeWholeHeaderViewPaddingTop(distance);
    }


    /**
     * 设置下拉刷新上拉加载更多控件，该方法是设置BaseRefreshViewHolder给RefreshLayout时由RefreshLayout调用
     */
    public void setRefreshLayout(BaseRefreshLayout baseRefreshLayout) {
        mBaseRefreshLayout = baseRefreshLayout;
    }


}
