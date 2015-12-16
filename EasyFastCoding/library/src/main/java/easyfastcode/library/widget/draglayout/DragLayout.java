package easyfastcode.library.widget.draglayout;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by 杨强彪 on 2015/11/8.
 *
 * @描述：这是侧滑自定义组件
 */
public class DragLayout extends FrameLayout {

    private static final String TAG = "DragLayout";
    private View mLeftView;
    private float mLeftWidth;
    private View mRightView;
    private float mRightWidth;
    private View mMiddleView;
    private boolean hasLeft;
    private boolean hasRight;
    private ViewDragHelper mDragHelper;
    private int mMinVelocity;
    private float downX;
    private float downY;
    // 左右侧view是否可打开 ( 默认左右都可以打开 )
    private boolean canOpenLeft = true;
    private boolean canOpenRight = true;
    private boolean isTouching; // 手是否在触摸

    private int mCurrentState = STATE_IDLE;//默认为闲置状态
    public static final int STATE_IDLE = 0;//空闲状态
    public static final int STATE_DRAGING = 1;//正在拖拽的状态
    public static final int STATE_FLING = 2;//快速的滑动的状态
    private boolean isLeftOpened = false;//用来记录左侧是否打开
    private boolean isRightOpened = false;//用来记录右侧是否打开
    private OnLayoutDragListener mListener;//拖拽的监听


    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 获取系统参考的最小速度
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mMinVelocity = configuration.getScaledMinimumFlingVelocity();
    }

    /**
     * 组件布局完成的方法
     * 获取子view，及其宽度
     * 控制侧滑，子view包括中间view在2~3个之间
     * 否则抛异常。
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // xml 文件加载完成回调
        int childCount = getChildCount();
        // 不能超过3个，不能少于2个
        if (childCount > 3 || childCount < 2) {
            throw new IllegalArgumentException("控件个数应该为2个或者3个");
        }
        for (int i = 0; i < childCount; i++) {

            View view = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();

            switch (layoutParams.gravity) {
                case Gravity.LEFT: // 左边布局
                    mLeftView = view;
                    mLeftWidth = layoutParams.width;
                    break;

                case Gravity.RIGHT: // 右边布局
                    mRightView = view;
                    mRightWidth = layoutParams.width;
                    break;

                default:  // 中间布局
                    mMiddleView = view;
                    break;
            }
        }

        // 要有中间的view左侧和右侧
        if (mMiddleView == null) {
            throw new IllegalArgumentException("没有主View");
        }
        // 给是否有左右布局做个标记
        hasLeft = mLeftView != null;
        hasRight = mRightView != null;

        // 初始化viewDragHelper，来实现拖动
        mDragHelper = ViewDragHelper.create(this, new DragCallback());

        // 初始化进入让其右滑动一个点，抢夺到touch事件，防止被child的view抢夺事件（如孩子中有ScroolView之类的）
        mDragHelper.smoothSlideViewTo(mMiddleView, -1, 0);
    }

    /**
     * 事件分发回调代码
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "downX-------------------");
                isTouching = true;
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "MOVE-------------------");
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isTouching = false;
                float upX = ev.getX();
                float upY = ev.getY();

                if (isLeftOpened || isRightOpened) {
                    if (upX > downX - 2 && upX < downX + 2 && upY > downY - 2 && upY < downY + 2) {
                        // 默认是点击事件
                        // 如果是左边的是打开的就让它关闭
                        Log.i(TAG, "点击 ： upX =" + upX + " upY =" + upY);
                        int left = mMiddleView.getLeft();
                        int top = mMiddleView.getTop();
                        int right = mMiddleView.getRight();
                        int bottom = mMiddleView.getBottom();
                        if (downX > left && downX < right && downY > top && downY < bottom) {
                            // 点击在主view上面
                            Log.i(TAG, "点击在主View上");
                            mDragHelper.smoothSlideViewTo(mMiddleView, 0, 0);
                            invalidate();
                            //   return true; // 不分发touch
                        }

                    }
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 让mDragHelper分析事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    /**
     * 计算滑动，写了这个方法才能真正自动回弹
     */
    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }

    }

    class DragCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View view, int i) {
            // 返回true就是这个view可以拖动，false是不可拖动
            return mMiddleView == view;
        }

        /**
         * 水平拖拽回调方法
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            // 这个表明水平拖动
            if (child == mMiddleView) {

                if (left > 0 && left > mLeftWidth) {
                    // 从左侧拖，但不能大于左侧控件宽度
                    return (int) (mLeftWidth + 0.5f);

                } else if (left < 0 && -left > mRightWidth) {
                    // 从右侧拖，但不能大于右侧控件宽度
                    return -(int) (mRightWidth + 0.5f);
                } else if (left > 0 && !canOpenLeft) {
                    return 0;
                } else if (left < 0 && !canOpenRight) {
                    return 0;
                }
            }
            return left;

        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (changedView == mMiddleView) {
                // 三个view都需要做动画，这个是缩放动画
                leftViewDoAnimation(left);
                middleViewDoAnimation(left);
                rightViewDoAnimation(left);
            }

            //  下面的逻辑都是在做状态的记录

            if (mCurrentState != STATE_DRAGING && mCurrentState != STATE_FLING) {

                if (left != 0 && left != mLeftWidth && left != -mRightWidth) {
                    // 正在拖动 #####
                    mCurrentState = STATE_DRAGING;

                    // 回调接口
                    if (mListener != null) {
                        mListener.onDragStateChanged(mCurrentState);
                    }
                }
            }

            // 逻辑-->关闭
            if (left > 0) {
                closeRightView();
            } else if (left == 0) {
                closeLeftView();
                closeRightView();

                //手指需要抬起,现在是闲置状态 ####
                mCurrentState = STATE_IDLE;

                // 回调接口
                if (mListener != null) {
                    mListener.onDragStateChanged(mCurrentState);
                }

            } else {
                closeLeftView();
            }

            // 闲置状态的逻辑-->左侧打开
            if (left == mLeftWidth) {
                openLeftView();
                //手指需要抬起,现在是闲置状态 ####
                mCurrentState = STATE_IDLE;
                // 回调接口
                if (mListener != null) {
                    mListener.onDragStateChanged(mCurrentState);
                }
            }

            // 闲置状态的逻辑-->右侧打开
            if (left == -mRightWidth) {
                openRightView();
                //手指需要抬起,现在是闲置状态 ####
                mCurrentState = STATE_IDLE;
                // 回调接口
                if (mListener != null) {
                    mListener.onDragStateChanged(mCurrentState);
                }
            }


        }


        /**
         * 释放拖拽回调的方法
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {

            // 判断释放点，做相应的回位动画
            if (releasedChild == mMiddleView) {
                int left = mMiddleView.getLeft();
                if (left > 0) {
                    // 打开一点，但速度大于系统给的最小速度也是可以的
                    if (xvel > mMinVelocity && canOpenLeft) {
                        // 左侧展开 ，放大。中间相反
                        mDragHelper.smoothSlideViewTo(mMiddleView, (int) (mLeftWidth + 0.5f), 0);

                        //改变状态 ####
                        mCurrentState = STATE_FLING;
                        // 回调接口
                        if (mListener != null) {
                            mListener.onDragStateChanged(mCurrentState);
                        }
                    } else if (-xvel > mMinVelocity && left > 0) {
                        mDragHelper.smoothSlideViewTo(mMiddleView, 0, 0);

                    } else {
                        if (left > mLeftWidth / 2) {
                            // 左侧展开 ，放大。中间相反
                            mDragHelper.smoothSlideViewTo(mMiddleView, (int) (mLeftWidth + 0.5f), 0);
                        } else {
                            // 左侧关闭 ，缩小 。中间相反
                            mDragHelper.smoothSlideViewTo(mMiddleView, 0, 0);

                        }
                    }

                } else {
                    if (-xvel > mMinVelocity && canOpenRight) {
                        // 右侧展开 ，放大。中间相反
                        mDragHelper.smoothSlideViewTo(mMiddleView, -(int) (mRightWidth + 0.5f), 0);

                        //改变状态 ####
                        mCurrentState = STATE_FLING;
                        // 回调接口
                        if (mListener != null) {
                            mListener.onDragStateChanged(mCurrentState);
                        }

                    } else if (xvel > mMinVelocity && left < 0) {
                        mDragHelper.smoothSlideViewTo(mMiddleView, 0, 0);

                    } else {
                        if (-left > mRightWidth / 2) {
                            // 右侧展开 ，放大。中间相反
                            mDragHelper.smoothSlideViewTo(mMiddleView, -(int) (mRightWidth + 0.5f), 0);

                        } else {
                            // 右侧关闭 ，缩小。中间相反
                            mDragHelper.smoothSlideViewTo(mMiddleView, 0, 0);

                        }
                    }

                }
            }
            invalidate();
        }
    }

    /**
     * 左侧关闭
     */
    public void closeLeftView() {
        if (isLeftOpened) {
            // 关闭了左侧
            //如果从右边往左边走，增量值为 负数
            Log.d(TAG, "关闭左侧");
            isLeftOpened = false;
            //            // 通知左边enable开关
            //            notifyLeftToggle();
            if (mListener != null) {
                mListener.onLeftToggle(isLeftOpened);
            }
        }
    }

    /**
     * 左侧打开
     */
    public void openLeftView() {
        if (!isLeftOpened) {
            // 打开了左侧
            Log.d(TAG, "打开了左侧");
            isLeftOpened = true;
            //            // 通知左边enable开关
            //            notifyLeftToggle();
            if (mListener != null) {
                mListener.onLeftToggle(isLeftOpened);
            }
        }
    }


    /**
     * 右侧打开
     */
    public void openRightView() {
        if (!isRightOpened) {
            // 打开了右侧
            Log.d(TAG, "打开了右侧");
            isRightOpened = true;
            //            // 通知右边enable开关
            //            notifyRightToggle();
            if (mListener != null) {
                mListener.onRightToggle(isRightOpened);
            }
        }
    }

    /**
     * 右侧关闭
     */
    public void closeRightView() {
        // 右侧关闭
        if (isRightOpened) {
            // 关闭了右侧
            Log.d(TAG, "关闭右侧");
            isRightOpened = false;
            //            // 通知右边enable开关
            //            notifyRightToggle();
            if (mListener != null) {
                mListener.onRightToggle(isRightOpened);
            }
        }
    }


    /**
     * 中间view做缩放动画
     */
    private void middleViewDoAnimation(int left) {

        if (left > 0) {
            // 从左往右滑，缩小
            ViewHelper.setPivotX(mMiddleView, mMiddleView.getMeasuredWidth() / 2);
            ViewHelper.setPivotY(mMiddleView, mMiddleView.getMeasuredHeight() / 2);
            // 缩放
            float scale = (float) (1 - 0.2 * left / mLeftWidth);
            ViewHelper.setScaleX(mMiddleView, scale);
            ViewHelper.setScaleY(mMiddleView, scale);
            // 做个透明动画
            ViewHelper.setAlpha(mMiddleView, (float) (1 - 0.5 * left / mLeftWidth));
        } else {
            // 从右往左滑，放大
            ViewHelper.setPivotX(mMiddleView, mMiddleView.getMeasuredWidth() / 2);
            ViewHelper.setPivotY(mMiddleView, mMiddleView.getMeasuredHeight() / 2);
            // 缩放
            float scale = (float) (1 + 0.2 * left / mRightWidth);
            ViewHelper.setScaleX(mMiddleView, scale);
            ViewHelper.setScaleY(mMiddleView, scale);
            // 做个透明动画
            ViewHelper.setAlpha(mMiddleView, (float) (1 + 0.5 * left / mRightWidth));
        }

    }

    /**
     * 左侧view做缩放属性动画
     */
    private void leftViewDoAnimation(int left) {

        if (left <= 0 || !hasLeft) {
            return;
        }

        mLeftView.setVisibility(View.VISIBLE);
        if (hasRight) {
            mRightView.setVisibility(View.GONE);
        }

        // 设置中轴锚点
        ViewHelper.setPivotX(mLeftView, mLeftView.getLeft());
        ViewHelper.setPivotY(mLeftView, mLeftView.getMeasuredHeight() / 2);
        // 缩放
        float scale = (float) (0.3 + 0.7 * left / mLeftWidth);
        ViewHelper.setScaleX(mLeftView, scale);
        ViewHelper.setScaleY(mLeftView, scale);
        ViewHelper.setAlpha(mLeftView, scale);

    }

    /**
     * 右侧view做缩放动画
     */
    private void rightViewDoAnimation(int left) {

        if (left >= 0 || !hasRight) {
            return;
        }

        mRightView.setVisibility(View.VISIBLE);
        if (hasLeft) {
            mLeftView.setVisibility(View.GONE);
        }
        // 设置中轴锚点
        ViewHelper.setPivotX(mRightView, mRightView.getMeasuredWidth());
        ViewHelper.setPivotY(mRightView, mRightView.getMeasuredHeight() / 2);
        // 缩放
        float scale = (float) (0.3 - 0.7 * left / mRightWidth);
        ViewHelper.setScaleX(mRightView, scale);
        ViewHelper.setScaleY(mRightView, scale);
        ViewHelper.setAlpha(mRightView, scale);

    }

    /**
     * 设置左边view 与右边的view 是否可拖拽打开
     */
    public void setCanOpenView(boolean canOpenLeft, boolean canOpenRight) {
        this.canOpenLeft = canOpenLeft;
        this.canOpenRight = canOpenRight;
    }

    /**
     * 操作 ：打开右边
     */
    public void openRight() {
        // 打开右边
        if (hasRight && !isRightOpened && canOpenRight) {
            //动画的关闭右侧
            mDragHelper.smoothSlideViewTo(mMiddleView, -(int) (mRightWidth + 0.5f), 0);
            invalidate();
        }
    }

    /**
     * 操作 ：关闭右边
     */
    public void closeRight() {
        // 关闭右边
        if (hasRight && isRightOpened && canOpenRight) {
            //动画的关闭右侧
            mDragHelper.smoothSlideViewTo(mMiddleView, 0, 0);
            invalidate();
        }
    }

    /**
     * 操作 ：关闭左边
     */
    public void closeLeft() {
        // 关闭左边
        if (hasRight && isLeftOpened && canOpenLeft) {
            //动画的关闭左侧
            mDragHelper.smoothSlideViewTo(mMiddleView, 0, 0);
            invalidate();
        }
    }

    /**
     * 操作 ：打开左边
     */
    public void openLeft() {
        // 展开左边
        if (hasRight && !isLeftOpened && canOpenLeft) {
            //展开左边
            mDragHelper.smoothSlideViewTo(mMiddleView, (int) (mLeftWidth + 0.5f), 0);
            invalidate();
        }

    }

    /**
     * 回调监听接口，暴露控件状态
     */
    public interface OnLayoutDragListener {
        //用来暴露左侧是打开还是关闭的
        void onLeftToggle(boolean open);

        //用来暴露右侧是打开还是关闭的
        void onRightToggle(boolean open);

        //当状态改变时的回调
        void onDragStateChanged(int state);
    }

    /**
     * 置回调监听
     */
    public void setOnLayoutDragListener(OnLayoutDragListener listener) {
        this.mListener = listener;
    }

    //    private void notifyLeftToggle() {
    //        if (isLeftOpened) {
    //            //如果左侧是打开，那么中间部分的view的功能应该不可用
    //            setViewEnable(mLeftView,
    //                    true);
    //            setViewEnable(mMiddleView,
    //                    false);
    //        } else {
    //            //如果左侧是关闭，左侧部分的view的功能也不可用
    //            setViewEnable(mLeftView,
    //                    false);
    //            setViewEnable(mMiddleView,
    //                    true);
    //        }
    //        if (mListener != null) {
    //            mListener.onLeftToggle(isLeftOpened);
    //        }
    //    }

    //    private void notifyRightToggle() {
    //        if (isRightOpened) {
    //            //如果右侧是打开，那么中间部分的view的功能应该不可用
    //            setViewEnable(mRightView, true);
    //            setViewEnable(mMiddleView, false);
    //        } else {
    //            //如果右侧是关闭，左侧部分的view的功能也不可用
    //            setViewEnable(mRightView, false);
    //            setViewEnable(mMiddleView, true);
    //        }
    //        if (mListener != null) {
    //            mListener.onRightToggle(isRightOpened);
    //        }
    //    }

    //    /**
    //     * viewGroup，以及其子类，递归设置是否可用
    //     */
    //    public void setViewEnable(View view, boolean enable) {
    //
    //        if (view == null) {
    //            return;
    //        }
    //        view.setEnabled(enable);
    //        if (view instanceof ViewGroup) {
    //            int count = ((ViewGroup) view).getChildCount();
    //            for (int i = 0; i < count; i++) {
    //                View child = ((ViewGroup) view).getChildAt(i);
    //                child.setEnabled(enable);
    //                //让孩子的孩子enable
    //                setViewEnable(child, enable);
    //            }
    //        }
    //    }

}
