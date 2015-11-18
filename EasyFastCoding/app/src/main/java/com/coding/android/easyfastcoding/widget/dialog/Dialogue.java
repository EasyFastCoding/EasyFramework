package com.coding.android.easyfastcoding.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.coding.android.easyfastcoding.R;

/**
 * 显示成功，失败，提示，Loading的对话框
 * <p/>
 * Created by cy on 2015/11/17.
 */
public class Dialogue {

    private Context mContext;
    private static Dialogue mLoading;
    private static final long DISMISS_DELAY = 1000;
    private DialogType mDialogType;

    private ViewGroup mDecorView;
    private ViewGroup mRootView;
    private DialogueView mDialogView;

    private Animation inAnim;
    private Animation outAnim;

    private final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

    //相关配置
    /**
     * 进度框位置
     */
    private int gravity = Gravity.CENTER;

    public enum DialogType {
        /**
         * 允许遮罩下面控件点击
         */
        None,
        /**
         * 不允许遮罩下面控件点击
         */
        Clear,
        /**
         * 不允许遮罩下面控件点击，背景黑色半透明
         */
        Black,
        /**
         * 不允许遮罩下面控件点击，背景渐变半透明
         */
        Gradient,
        /**
         * 不允许遮罩下面控件点击，点击遮罩消失
         */
        ClearCancel,
        /**
         * 不允许遮罩下面控件点击，背景黑色半透明，点击遮罩消失
         */
        BlackCancel,
        /**
         * 不允许遮罩下面控件点击，背景渐变半透明，点击遮罩消失
         */
        GradientCancel
    }

    /**
     * ++++++++++++++++++++++++++++++++++++++++++++初始化++++++++++++++++++++++++++++++++++++
     */

    private Dialogue() {
    }

    private static final Dialogue getInstance(Context context) {
        if (mLoading == null) {
            mLoading = new Dialogue();
            mLoading.mContext = context;
            mLoading.initViews();
            mLoading.initDefaultView();
            mLoading.initAnimation();
        }
        if (context != null && context != mLoading.mContext) {
            mLoading.mContext = context;
            mLoading.initViews();
        }

        return mLoading;
    }

    protected void initViews() {
        gravity = Gravity.CENTER;

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        mDecorView = (ViewGroup) ((Activity) mContext).getWindow().getDecorView().findViewById(android.R.id.content);
        mRootView = (ViewGroup) layoutInflater.inflate(R.layout.layout_progress, null, false);
        mRootView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    protected void initDefaultView() {
        mDialogView = new DialogueView(mContext);
        params.gravity = gravity;
        mDialogView.setLayoutParams(params);
    }

    protected void initAnimation() {
        if (inAnim == null) {
            inAnim = getInAnimation();
        }
        if (outAnim == null) {
            outAnim = getOutAnimation();
        }
    }

    public Animation getInAnimation() {
        int res = getAnimationResource(this.gravity, true);
        return AnimationUtils.loadAnimation(mContext, res);
    }

    public Animation getOutAnimation() {
        int res = getAnimationResource(this.gravity, false);
        return AnimationUtils.loadAnimation(mContext, res);
    }

    private int getAnimationResource(int gravity, boolean isInAnimation) {
        switch (gravity) {
            case Gravity.TOP:
                return isInAnimation ? R.anim.loading_slidein_from_top : R.anim.loading_slideout_to_top;
            case Gravity.BOTTOM:
                return isInAnimation ? R.anim.loading_slidein_from_bottom : R.anim.loading_slideout_to_bottom;
            case Gravity.CENTER:
                return isInAnimation ? R.anim.loading_fadein_to_center : R.anim.loading_fadeout_from_center;
            default:
                // This case is not implemented because we don't expect any other gravity at the moment
        }
        return -1;
    }


    /**
     * 执行加载框消失动画
     */
    public void dismiss() {
        outAnim.setAnimationListener(outAnimListener);
        mDialogView.startAnimation(outAnim);
    }

    /**
     * 消失动画执行完，重置到最初状态
     */
    public void dismissImmediately() {
        mDialogView.dismiss();
        mRootView.removeView(mDialogView);
        mDecorView.removeView(mRootView);
        mContext = null;
    }

    public void show() {
        mHandler.removeCallbacksAndMessages(null);
        if (!isShowing()) {
            onAttached();
        }
        mDialogView.startAnimation(inAnim);
    }

    /**
     * 检测view是否被添加到根视图上
     *
     * @return
     */
    public boolean isShowing() {
        return mRootView.getParent() != null;
    }

    /**
     * +++++++++++++++++++++++++++++++++++++设置的方法+++++++++++++++++++++++++++++++++++++++++
     */
    /**
     * 将loading View添加到Activity的根视图
     */
    private void onAttached() {
        mDecorView.addView(mRootView);
        if (mDialogView.getParent() != null)
            ((ViewGroup) mDialogView.getParent()).removeView(mDialogView);
        mRootView.addView(mDialogView);
    }

    private void scheduleDismiss() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendEmptyMessageDelayed(0, DISMISS_DELAY);
    }

    private void setDialogType(DialogType type) {
        mDialogType = type;
        switch (mDialogType) {
            case None:
                configDialogType(android.R.color.transparent, false, false);
                break;
            case Clear:
                configDialogType(android.R.color.transparent, true, false);
                break;
            case ClearCancel:
                configDialogType(android.R.color.transparent, true, true);
                break;
            case Black:
                configDialogType(R.color.bg_overlay, true, false);
                break;
            case BlackCancel:
                configDialogType(R.color.bg_overlay, true, true);
                break;
            case Gradient:
                //TODO 设置半透明渐变背景
                configDialogType(R.drawable.bg_overlay_gradient, true, false);
                break;
            case GradientCancel:
                //TODO 设置半透明渐变背景
                configDialogType(R.drawable.bg_overlay_gradient, true, true);
                break;
            default:
                break;
        }

    }

    private void configDialogType(int bg, boolean clickable, boolean cancelable) {
        mRootView.setBackgroundResource(bg);
        mRootView.setClickable(clickable);
        setCancelable(cancelable);
    }

    /**
     * 能否通过点击加载框外面来隐藏加载框
     *
     * @param isCancelable
     */
    private void setCancelable(boolean isCancelable) {
        View view = mRootView.findViewById(R.id.progress_container);

        if (isCancelable) {
            view.setOnTouchListener(onCancelableTouchListener);
        } else {
            view.setOnTouchListener(null);
        }
    }

    /**
     * =================================提供给外界的静态方法======================================
     */

    /**
     * 显示loading框
     *
     * @param context
     */
    public static void show(Context context) {
        getInstance(context).setDialogType(DialogType.Black);
        getInstance(context).mDialogView.show();
        getInstance(context).show();
    }

    /**
     * 自定义Loading显示类型
     *
     * @param context
     * @param type
     */
    public static void showWithType(Context context, DialogType type) {
        getInstance(context).setDialogType(type);
        getInstance(context).mDialogView.show();
        getInstance(context).show();
    }

    /**
     * 显示加载中的框
     *
     * @param context
     * @param string
     */
    public static void showWithStatus(Context context, String string) {
        getInstance(context).setDialogType(DialogType.Black);
        getInstance(context).mDialogView.showWithStatus(string);
        getInstance(context).show();
    }

    public static void showWithStatus(Context context, String string, DialogType Type) {
        getInstance(context).setDialogType(Type);
        getInstance(context).mDialogView.showWithStatus(string);
        getInstance(context).show();
    }

    /**
     * 显示提示对话框，10秒后关闭
     *
     * @param context
     * @param string
     */
    public static void showInfoWithStatus(Context context, String string) {
        getInstance(context).setDialogType(DialogType.Black);
        getInstance(context).mDialogView.showInfoWithStatus(string);
        getInstance(context).show();
        getInstance(context).scheduleDismiss();
    }

    public static void showInfoWithStatus(Context context, String string, DialogType maskType) {
        getInstance(context).setDialogType(maskType);
        getInstance(context).mDialogView.showInfoWithStatus(string);
        getInstance(context).show();
        getInstance(context).scheduleDismiss();
    }

    /**
     * 显示成功提示，10秒消失
     *
     * @param context
     * @param string
     */
    public static void showSuccessWithStatus(Context context, String string) {
        getInstance(context).setDialogType(DialogType.Black);
        getInstance(context).mDialogView.showSuccessWithStatus(string);
        getInstance(context).show();
        getInstance(context).scheduleDismiss();
    }

    public static void showSuccessWithStatus(Context context, String string, DialogType maskType) {
        getInstance(context).setDialogType(maskType);
        getInstance(context).mDialogView.showSuccessWithStatus(string);
        getInstance(context).show();
        getInstance(context).scheduleDismiss();
    }

    /**
     * 显示失败提示
     *
     * @param context
     * @param string
     */
    public static void showErrorWithStatus(Context context, String string) {
        getInstance(context).setDialogType(DialogType.Black);
        getInstance(context).mDialogView.showErrorWithStatus(string);
        getInstance(context).show();
        getInstance(context).scheduleDismiss();
    }

    public static void showErrorWithStatus(Context context, String string, DialogType maskType) {
        getInstance(context).setDialogType(maskType);
        getInstance(context).mDialogView.showErrorWithStatus(string);
        getInstance(context).show();
        getInstance(context).scheduleDismiss();
    }

    /**
     * 显示有百分比的进度条对话框
     *
     * @param context
     * @param string
     * @param maskType
     */
    public static void showWithProgress(Context context, String string, DialogType maskType) {
        getInstance(context).setDialogType(maskType);
        getInstance(context).mDialogView.showWithProgress(string);
        getInstance(context).show();
    }

    /**
     * 得到圆形的进度条对象
     *
     * @param context
     * @return
     */
    public static CircleProgressBar getProgressBar(Context context) {
        return getInstance(context).mDialogView.getCircleProgressBar();
    }

    /**
     * 设置提示文字
     *
     * @param context
     * @param string
     */
    public static void setText(Context context, String string) {
        getInstance(context).mDialogView.setText(string);
    }


    /**
     * loading框是否在显示
     *
     * @param context
     * @return
     */
    public static boolean isShowing(Context context) {
        return getInstance(context).mRootView.getParent() != null;
    }

    /**
     * ==========================================================================
     */

    private final View.OnTouchListener onCancelableTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                dismiss();
                setCancelable(false);
            }
            return false;
        }
    };

    Animation.AnimationListener outAnimListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            dismissImmediately();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dismiss();
        }
    };

}
