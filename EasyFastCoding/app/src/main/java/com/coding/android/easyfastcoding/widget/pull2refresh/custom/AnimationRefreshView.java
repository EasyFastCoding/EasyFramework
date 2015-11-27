package com.coding.android.easyfastcoding.widget.pull2refresh.custom;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.coding.android.easyfastcoding.R;

/**
 * Created by cy on 2015/11/27.
 */
public class AnimationRefreshView extends RelativeLayout {

    private ImageView iv_anim_pulldown;
    private ImageView iv_anim_release;
    /**
     * 从下拉状态到准备释放状态的变换动画
     */
    private AnimationDrawable mChangeToReleaseAD;
    /**
     * 释放之后的动画
     */
    private AnimationDrawable mReleaseAD;

    private int mChangeToReleaseAnimResId;
    private int mRefreshAnimResId;

    public AnimationRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        iv_anim_pulldown = (ImageView) findViewById(R.id.iv_anim_pulldown);
        iv_anim_release = (ImageView) findViewById(R.id.iv_anim_release);
    }

    /**
     * 设置下拉过程中显示图片
     */
    public void setPullDownImageResource(@DrawableRes int resId) {
        iv_anim_pulldown.setImageResource(resId);
    }

    /**
     * 设置下拉状态到准备释放状态的动画
     */
    @SuppressWarnings("ResourceType")
    public void setChangeToReleaseAnimResId(@AnimatorRes int resId) {
        mChangeToReleaseAnimResId = resId;
        iv_anim_release.setImageResource(mChangeToReleaseAnimResId);
    }

    /**
     * 设置刷新动画
     */
    public void setRefreshAnimResId(@AnimRes int resId) {
        mRefreshAnimResId = resId;
    }

    /**
     * 1.回到初始状态
     */
    public void changeToIdle() {
        stopChangeTopReleaseRefreshAnimationDrawable();
        stopRefreshAnimtionDrawable();

        iv_anim_pulldown.setVisibility(VISIBLE);
        iv_anim_release.setVisibility(INVISIBLE);
    }

    /**
     * 2.开始下拉状态
     */
    public void changeToPullDown() {
        iv_anim_pulldown.setVisibility(VISIBLE);
        iv_anim_release.setVisibility(INVISIBLE);
    }

    /**
     * 3.从下拉状态到准备刷新状态
     */
    public void changeToReleaseRefresh() {
        iv_anim_release.setImageResource(mChangeToReleaseAnimResId);
        mChangeToReleaseAD = (AnimationDrawable) iv_anim_release.getDrawable();

        iv_anim_release.setVisibility(VISIBLE);
        iv_anim_pulldown.setVisibility(INVISIBLE);

        mChangeToReleaseAD.start();
    }

    /**
     * 4.刷新状态
     */
    public void changeToRefreshing() {
        stopChangeTopReleaseRefreshAnimationDrawable();

        iv_anim_release.setImageResource(mRefreshAnimResId);
        mReleaseAD = (AnimationDrawable) iv_anim_release.getDrawable();

        iv_anim_release.setVisibility(VISIBLE);
        iv_anim_pulldown.setVisibility(INVISIBLE);

        mReleaseAD.start();
    }

    /**
     * 5.结束刷新
     */
    public void onEndRefreshing() {
        stopChangeTopReleaseRefreshAnimationDrawable();
        stopRefreshAnimtionDrawable();
    }

    /**
     * 整个刷新流程 1 - 2 - 3 - 4 - 5 - 2 - 1
     */

    public void handleScale(float scale) {
        scale = 0.1f + 0.9f * scale;
        ViewCompat.setScaleX(iv_anim_pulldown, scale);
        ViewCompat.setPivotY(iv_anim_pulldown, iv_anim_pulldown.getHeight());
        ViewCompat.setScaleY(iv_anim_pulldown, scale);
    }

    /**
     * 停止并销毁 从下拉状态到准备释放状态的变换动画
     */
    public void stopChangeTopReleaseRefreshAnimationDrawable() {
        if (mChangeToReleaseAD != null) {
            mChangeToReleaseAD.stop();
            mChangeToReleaseAD = null;
        }
    }

    /**
     * 停止并销毁 刷新动画
     */
    public void stopRefreshAnimtionDrawable() {
        if (mReleaseAD != null) {
            mReleaseAD.stop();
            mReleaseAD = null;
        }
    }

}
