package easyfastcode.library.widget.pull2refresh.custom;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.AnimRes;
import android.support.annotation.DrawableRes;
import android.view.View;

import easyfastcode.library.R;
import easyfastcode.library.widget.pull2refresh.base.BaseRefreshViewHolder;

/**
 * Created by cy on 2015/11/26.
 * <p/>
 * 刷新动画
 * <p/>
 * 几个必须调用的设置方法
 * <br/>
 * 1、setPullDownImageResource：设置下拉过程中的图片
 * <br/>
 * 2、setChangeToReleaseRefreshAnimResId：设置进入释放刷新状态时的动画
 * <br/>
 * 3、setRefreshAnimResId：设置正在刷星是的动画
 */
public class AnimationRefreshViewHolder extends BaseRefreshViewHolder {

    private AnimationRefreshView mAnimationRefreshView;
    private int mPullDownImageResId = -1;
    private int mChangeToReleaseRefreshAnimResId = -1;
    private int mRefreshingAnimResId = -1;

    /**
     * @param context
     * @param isLoadingMoreEnabled 上拉加载更多是否可用
     */
    public AnimationRefreshViewHolder(Context context, boolean isLoadingMoreEnabled) {
        super(context, isLoadingMoreEnabled);
    }

    /**
     * 设置下拉过程中的图片资源
     *
     * @param resId
     */
    public void setPullDownImageResource(@DrawableRes int resId) {
        mPullDownImageResId = resId;
    }

    /**
     * 设置进入释放刷新状态时的动画资源
     *
     * @param resId
     */
    public void setChangeToReleaseRefreshAnimResId(@AnimRes int resId) {
        mChangeToReleaseRefreshAnimResId = resId;
    }

    /**
     * 设置正在刷新时的动画资源
     *
     * @param resId
     */
    public void setRefreshingAnimResId(@AnimRes int resId) {
        mRefreshingAnimResId = resId;
    }

    @Override
    public View getRefreshView() {
        if (mRefreshView == null) {
            mRefreshView = View.inflate(mContext, R.layout.ef_view_refresh_header_with_animation, null);
            mRefreshView.setBackgroundColor(Color.TRANSPARENT);
            if (mRefreshBgColorRes != -1) {
                mRefreshView.setBackgroundResource(mRefreshBgColorRes);
            }
            if (mRefreshBgDrawableRes != -1) {
                mRefreshView.setBackgroundResource(mRefreshBgDrawableRes);
            }

            mAnimationRefreshView = (AnimationRefreshView) mRefreshView.findViewById(R.id.anim_container);
            if (mPullDownImageResId != -1) {
                mAnimationRefreshView.setPullDownImageResource(mPullDownImageResId);
            } else {
                throw new RuntimeException("请调用" + AnimationRefreshViewHolder.class.getSimpleName() + "的setPullDownImageResource方法设置下拉过程中的图片资源");
            }
            if (mChangeToReleaseRefreshAnimResId != -1) {
                mAnimationRefreshView.setChangeToReleaseAnimResId(mChangeToReleaseRefreshAnimResId);
            } else {
                throw new RuntimeException("请调用" + AnimationRefreshViewHolder.class.getSimpleName() + "的setChangeToReleaseRefreshAnimResId方法设置进入释放刷新状态时的动画资源");
            }
            if (mRefreshingAnimResId != -1) {
                mAnimationRefreshView.setRefreshAnimResId(mRefreshingAnimResId);
            } else {
                throw new RuntimeException("请调用" + AnimationRefreshViewHolder.class.getSimpleName() + "的setRefreshingAnimResId方法设置正在刷新时的动画资源");
            }
        }
        return mRefreshView;
    }

    @Override
    public void handleScale(float scale, int moveYDistance) {
        if (scale <= 1.0f) {
            mAnimationRefreshView.handleScale(scale);
        }
    }

    @Override
    public void changeToIdle() {
        mAnimationRefreshView.changeToIdle();
    }

    @Override
    public void changeToPullDown() {
        mAnimationRefreshView.changeToPullDown();
    }

    @Override
    public void changeToReleaseRefresh() {
        mAnimationRefreshView.changeToReleaseRefresh();
    }

    @Override
    public void changeToRefreshing() {
        mAnimationRefreshView.changeToRefreshing();
    }

    @Override
    public void onEndRefreshing() {
        mAnimationRefreshView.onEndRefreshing();
    }
}
