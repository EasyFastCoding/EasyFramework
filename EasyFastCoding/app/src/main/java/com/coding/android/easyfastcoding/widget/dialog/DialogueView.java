package com.coding.android.easyfastcoding.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coding.android.easyfastcoding.R;

/**
 * 默认的loading
 * Created by cy on 2015/11/17.
 */
public class DialogueView extends LinearLayout {

    protected int mResBigLoadingImageId;
    protected int mResInfoImageId;
    protected int mResSuccessImageId;
    protected int mResErrorImageId;

    protected ImageView mBigLoadingImage;
    protected ImageView mSmallLoadingImage;

    protected TextView mMessageText;

    protected CircleProgressBar mProgressBar;

    protected RotateAnimation mRotateAnimation;

    public DialogueView(Context context) {
        super(context);
        initView();
        init();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.ef_view_default_progress, this, true);
        mBigLoadingImage = (ImageView) findViewById(R.id.iv_big_loading);
        mSmallLoadingImage = (ImageView) findViewById(R.id.iv_small_loading);
        mProgressBar = (CircleProgressBar) findViewById(R.id.circle_progress_bar);
        mMessageText = (TextView) findViewById(R.id.tv_msg);
    }

    private void init() {
        mRotateAnimation = new RotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setDuration(1000L);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setRepeatCount(-1);
        mRotateAnimation.setRepeatMode(Animation.RESTART);
    }

    public void show() {
        clearAnimations();
        mBigLoadingImage.setImageResource(mResBigLoadingImageId);
        mBigLoadingImage.setVisibility(View.VISIBLE);
        mSmallLoadingImage.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mMessageText.setVisibility(View.GONE);
        //开启旋转动画
        mBigLoadingImage.startAnimation(mRotateAnimation);
    }

    public void showWithStatus(String string) {
        if (string == null) {
            show();
            return;
        }
        showBaseStatus(mResBigLoadingImageId, string);
        //开启旋转动画
        mSmallLoadingImage.startAnimation(mRotateAnimation);
    }

    public void showInfoWithStatus(String string) {
        showBaseStatus(mResInfoImageId, string);
    }

    public void showSuccessWithStatus(String string) {
        showBaseStatus(mResSuccessImageId, string);
    }

    public void showErrorWithStatus(String string) {
        showBaseStatus(mResErrorImageId, string);
    }

    public void showWithProgress(String string) {
        showProgress(string);
    }

    public CircleProgressBar getCircleProgressBar() {
        return mProgressBar;
    }

    public void setText(String string) {
        mMessageText.setText(string);
    }

    public void showProgress(String string) {
        clearAnimations();
        mMessageText.setText(string);
        mBigLoadingImage.setVisibility(View.GONE);
        mSmallLoadingImage.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mMessageText.setVisibility(View.VISIBLE);
    }

    public void showBaseStatus(int res, String string) {
        clearAnimations();
        mSmallLoadingImage.setImageResource(res);
        mMessageText.setText(string);
        mBigLoadingImage.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mSmallLoadingImage.setVisibility(View.VISIBLE);
        mMessageText.setVisibility(View.VISIBLE);
    }

    public void dismiss() {
        clearAnimations();
    }

    private void clearAnimations() {
        mBigLoadingImage.clearAnimation();
        mSmallLoadingImage.clearAnimation();
    }


}
