package com.coding.android.easyfastcoding.widget.pull2refresh.base;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.lang.reflect.Field;

/**
 * 下拉刷新、上拉加载更多、可添加自定义（固定、可滑动）头部控件（例如:app顶部的广告位）
 *
 * 该view主要处理下拉，上拉手势事件，下拉及回弹等等。
 * Created by cy on 2015/11/20.
 */
public class BaseRefreshLayout extends LinearLayout {

    private static final String TAG = BaseRefreshLayout.class.getSimpleName();

    //========> private <=========
    //刷新view处理类
    private BaseRefreshViewHolder mRefreshViewHolder;
    //支持下拉的view
    private View mContentView;
    private AbsListView mAbsListView;
    private ScrollView mScrollView;
    private RecyclerView mRecyclerView;
    private View mNormalView;
    private WebView mWebView;
    /**
     * 整个头部控件，下拉刷新控件mRefreshHeaderView和下拉刷新控件下方的自定义组件mCustomHeaderView的父控件
     */
    private LinearLayout mWholeHeaderViewContainer;
    /**
     * 手指按下时，y轴方向的偏移量
     */
    private int mWholeHeaderDownY = -1;
    /**
     * 记录开始下拉刷新时的downY
     */
    private int mRefreshDownY = -1;
    /**
     * 按下时整个头部控件的paddingTop
     */
    private int mWholeHeaderViewDownPaddingTop = 0;
    /**
     * 整个头部控件最小的paddingTop，即下拉刷新view的高度
     */
    private int mMinWholeHeaderViewPaddingTop;
    /**
     * 整个头部控件最大的paddingTop，即支持的最大下拉高度
     */
    private int mMaxWholeHeaderViewPaddingTop;
    /**
     * 下拉刷新控件
     */
    private View mRefreshHeaderView;
    /**
     * 下拉刷新控件的高度
     */
    private int mRefreshHeaderViewHeight;
    /**
     * 上拉加载更多控件
     */
    private View mLoadMoreFooterView;
    /**
     * 上拉加载更多控件的高度
     */
    private int mLoadMoreFooterViewHeight;
    /**
     * 下拉刷新控件下方的自定义控件是否可滚动，默认不可滚动
     */
    private boolean mIsCustomHeaderViewScrollable = false;
    /**
     * 下拉刷新控件下方的自定义控件
     */
    private View mCustomHeaderView;
    /**
     * 下拉刷新和上拉加载更多代理
     */
    private RefreshLayoutDelegate mDelegate;
    /**
     * 是否已经设置内容控件滚动监听器
     */
    private boolean mIsInitedContentViewScrollListener = false;
    /**
     * 是否处于正在加载更多状态
     */
    private boolean mIsLoadingMore = false;
    /**
     * 触发上拉加载更多时是否显示加载更多控件
     */
    private boolean mIsShowLoadingMoreView = true;
    /**
     * 当前刷新状态
     */
    private RefreshStatus mCurrentRefreshStatus = RefreshStatus.IDLE;


    private float mInterceptTouchDownX = -1;
    private float mInterceptTouchDownY = -1;

    public enum RefreshStatus {
        /**
         * 空闲状态
         */
        IDLE,
        /**
         * 下拉状态
         */
        PULL_DOWN,
        /**
         * 释放刷新状态
         */
        RELEASE_REFRESH,
        /**
         * 刷新状态
         */
        REFRESHING
    }

    public interface RefreshLayoutDelegate {
        /**
         * 开始刷新
         */
        void onRefreshLayoutBeginRefreshing(BaseRefreshLayout baseRefreshLayout);

        /**
         * 开始加载更多。由于监听了ScrollView、RecyclerView、AbsListView滚动到底部的事件，所以这里采用返回boolean来处理是否加载更多。否则使用endLoadingMore方法会失效
         *
         * @param baseRefreshLayout
         * @return 如果要开始加载更多则返回true，否则返回false。（返回false的场景：没有网络、一共只有x页数据并且已经加载了x页数据了）
         */
        boolean onRefreshLayoutBeginLoadingMore(BaseRefreshLayout baseRefreshLayout);
    }

    /**
     * 整个头部控件，下拉刷新控件mRefreshHeaderView和下拉刷新控件下方的自定义组件mCustomHeaderView的父控件
     */
    private LinearLayout mHeaderContainer;

    private Handler mHandler;

    public BaseRefreshLayout(Context context) {
        this(context, null);
    }

    public BaseRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        mHandler = new Handler(Looper.getMainLooper());
        initHeaderContainer();
    }

    /**
     * 为下拉刷新的view，和刷新下面的view提供一个容器
     */
    private void initHeaderContainer() {
        mHeaderContainer = new LinearLayout(getContext());
        mHeaderContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        mHeaderContainer.setOrientation(LinearLayout.VERTICAL);
        addView(mHeaderContainer);
    }

    /**
     * 当view中所有的子控件都被inflate时候调用。
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (getChildCount() != 2) {
            throw new RuntimeException(BaseRefreshLayout.class.getSimpleName() + "必须有且只有一个子控件");
        }

        mContentView = getChildAt(1);
        if (mContentView instanceof AbsListView) {
            mAbsListView = (AbsListView) mContentView;
        } else if (mContentView instanceof RecyclerView) {
            mRecyclerView = (RecyclerView) mContentView;
        } else if (mContentView instanceof ScrollView) {
            mScrollView = (ScrollView) mContentView;
        } else if (mContentView instanceof WebView) {
            mWebView = (WebView) mContentView;
        }
        //TODO:
//        else if (mContentView instanceof BGAStickyNavLayout) {
//            mStickyNavLayout = (BGAStickyNavLayout) mContentView;
//            mStickyNavLayout.setRefreshLayout(this);
//        }
        else {
            mNormalView = mContentView;
            // 设置为可点击，否则在空白区域无法拖动
            mNormalView.setClickable(true);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        // 被添加到窗口后再设置监听器，这样开发者就不必烦恼先初始化RefreshLayout还是先设置自定义滚动监听器
        if (!mIsInitedContentViewScrollListener && mLoadMoreFooterView != null) {
            setRecyclerViewOnScrollListener();
            setAbsListViewOnScrollListener();

            //在初始化的时候addHeaderView,paddingTop值为0。要显示时才addFooterView
            addView(mLoadMoreFooterView, getChildCount());
            mIsInitedContentViewScrollListener = true;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mIsCustomHeaderViewScrollable && !isWholeHeaderViewCompleteInvisible()) {
            super.dispatchTouchEvent(ev);
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInterceptTouchDownX = event.getRawX();//event.getX是相对于widget左上角，而event.getRawX则是相对于屏幕左上角。
                mInterceptTouchDownY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mIsLoadingMore && (mCurrentRefreshStatus != RefreshStatus.REFRESHING)) {//当前不处于上拉加载 和 下拉刷新状态
                    if (mInterceptTouchDownX == -1) {
                        mInterceptTouchDownX = (int) event.getRawX();
                    }
                    if (mInterceptTouchDownY == -1) {
                        mInterceptTouchDownY = (int) event.getRawY();
                    }

                    int interceptTouchMoveDistanceY = (int) (event.getRawY() - mInterceptTouchDownY);
                    // 可以没有上拉加载更多，但是必须有下拉刷新，否则就不拦截事件
                    if (Math.abs(event.getRawX() - mInterceptTouchDownX) < Math.abs(interceptTouchMoveDistanceY) && mRefreshHeaderView != null) {//X方向移动距离比Y方向的大
                        if ((interceptTouchMoveDistanceY > 0 && shouldHandleRefresh()) || (interceptTouchMoveDistanceY < 0 && shouldHandleLoadingMore()) || interceptTouchMoveDistanceY < 0 && !isWholeHeaderViewCompleteInvisible()) {

                            // ACTION_DOWN时没有消耗掉事件，子控件会处于按下状态，这里设置ACTION_CANCEL，使子控件取消按下状态
                            event.setAction(MotionEvent.ACTION_CANCEL);
                            super.onInterceptTouchEvent(event);
                            return true;
                            //返回true被拦截的情况：当顶部的广告页面没有完全隐藏，同时支持上拉下拉刷新。这是listView或者scrollView的上下滑动事件会被拦截。
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                // 重置
                mInterceptTouchDownX = -1;
                mInterceptTouchDownY = -1;
                break;
        }

        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != mRefreshHeaderView) { //前提是能下拉刷新
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mWholeHeaderDownY = (int) event.getY();
                    if (mCustomHeaderView != null) {
                        mWholeHeaderViewDownPaddingTop = mWholeHeaderViewContainer.getPaddingTop();
                    }

                    if (mCustomHeaderView == null || !mIsCustomHeaderViewScrollable) {//不需要考虑头部自定义控件的事件
                        mRefreshDownY = (int) event.getY();
                    }
                    if (isWholeHeaderViewCompleteInvisible()) {//头部自定义控件不可见了
                        mRefreshDownY = (int) event.getY();
                        return true;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (handleActionMove(event)) {
                        return true;
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (handleActionUpOrCancel(event)) {
                        return true;
                    }
                    break;
                default:
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * ===================================== 内部私有方法 =================================
     */
    /**
     * 处理手指抬起事件
     *
     * @return true表示自己消耗掉该事件，false表示不消耗该事件
     */
    private boolean handleActionUpOrCancel(MotionEvent event) {
        boolean isReturnTrue = false;
        // 如果当前头部刷新控件没有完全隐藏，则需要返回true，自己消耗ACTION_UP事件
        if ((mCustomHeaderView == null || (mCustomHeaderView != null && !mIsCustomHeaderViewScrollable)) && mWholeHeaderViewContainer.getPaddingTop() != mMinWholeHeaderViewPaddingTop) {
            isReturnTrue = true;
        }

        if (mCurrentRefreshStatus == RefreshStatus.PULL_DOWN || mCurrentRefreshStatus == RefreshStatus.IDLE) {
            // 处于下拉刷新状态，松手时隐藏下拉刷新控件
            if (mCustomHeaderView == null || (mCustomHeaderView != null && mWholeHeaderViewContainer.getPaddingTop() < 0 && mWholeHeaderViewContainer.getPaddingTop() > mMinWholeHeaderViewPaddingTop)) {
                hiddenRefreshHeaderView();
            }
            mCurrentRefreshStatus = RefreshStatus.IDLE;
            handleRefreshStatusChanged();
        } else if (mCurrentRefreshStatus == RefreshStatus.RELEASE_REFRESH) {
            // 处于松开进入刷新状态，松手时完全显示下拉刷新控件，进入正在刷新状态
            beginRefreshing();
        }

        if (mRefreshDownY == -1) {
            mRefreshDownY = (int) event.getY();
        }
        int diffY = (int) event.getY() - mRefreshDownY;
        if (shouldHandleLoadingMore() && diffY <= 0) {
            // 处理上拉加载更多，需要返回true，自己消耗ACTION_UP事件
            isReturnTrue = true;
            beginLoadingMore();
        }

        mWholeHeaderDownY = -1;
        mRefreshDownY = -1;
        return isReturnTrue;
    }

    /**
     * 处理手指滑动事件
     *
     * @param event
     * @return true表示自己消耗掉该事件，false表示不消耗该事件
     */
    private boolean handleActionMove(MotionEvent event) {
        //1.当前处于刷新状态，则返回
        if (mCurrentRefreshStatus == RefreshStatus.REFRESHING || mIsLoadingMore) {
            return false;
        }

        //顶部自定view不需要处理触摸事件，记录下开始下滑的Y坐标
        if ((mCustomHeaderView == null || !mIsCustomHeaderViewScrollable) && mRefreshDownY == -1) {
            mRefreshDownY = (int) event.getY();
        }
        if (mCustomHeaderView != null && mIsCustomHeaderViewScrollable && isCustomHeaderViewCompleteVisible() && mRefreshDownY == -1) {
            mRefreshDownY = (int) event.getY();
        }

        //计算刷新view需要移动的距离
        int refreshDiffY = (int) event.getY() - mRefreshDownY;
        refreshDiffY = (int) (refreshDiffY / mRefreshViewHolder.getPaddingTopScale());

        // 如果是向下拉，并且当前可见的第一个条目的索引等于0，才处理整个头部控件的padding
        if (refreshDiffY > 0 && shouldHandleRefresh() && isCustomHeaderViewCompleteVisible()) {

            int paddingTop = mMinWholeHeaderViewPaddingTop + refreshDiffY;

            if (paddingTop > 0 && mCurrentRefreshStatus != RefreshStatus.RELEASE_REFRESH) {
                // 下拉刷新控件完全显示，并且当前状态没有处于释放开始刷新状态
                mCurrentRefreshStatus = RefreshStatus.RELEASE_REFRESH;
                // 更新刷新状态的回调
                handleRefreshStatusChanged();

                mRefreshViewHolder.handleScale(1.0f, refreshDiffY);
            } else if (paddingTop < 0) {
                // 下拉刷新控件没有完全显示，并且当前状态没有处于下拉刷新状态
                if (mCurrentRefreshStatus != RefreshStatus.PULL_DOWN) {
                    boolean isPreRefreshStatusNotIdle = mCurrentRefreshStatus != RefreshStatus.IDLE;
                    mCurrentRefreshStatus = RefreshStatus.PULL_DOWN;
                    if (isPreRefreshStatusNotIdle) {
                        handleRefreshStatusChanged();
                    }
                }
                //处理下拉进度
                float scale = 1 - paddingTop * 1.0f / mMinWholeHeaderViewPaddingTop;
                /**
                 * 往下滑
                 * paddingTop    mMinWholeHeaderViewPaddingTop 到 0
                 * scale         0 到 1
                 * 往上滑
                 * paddingTop    0 到 mMinWholeHeaderViewPaddingTop
                 * scale         1 到 0
                 */
                mRefreshViewHolder.handleScale(scale, refreshDiffY);
            }
            paddingTop = Math.min(paddingTop, mMaxWholeHeaderViewPaddingTop);
            mWholeHeaderViewContainer.setPadding(0, paddingTop, 0, 0);

            if (mRefreshViewHolder.canChangeToRefreshingStatus()) {
                mWholeHeaderDownY = -1;
                mRefreshDownY = -1;

                beginRefreshing();
            }

            return true;
        }


        if (mCustomHeaderView != null && mIsCustomHeaderViewScrollable) { //顶部自定义view需要处理滑动事件
            if (mWholeHeaderDownY == -1) {
                mWholeHeaderDownY = (int) event.getY();

                if (mCustomHeaderView != null) {
                    mWholeHeaderViewDownPaddingTop = mWholeHeaderViewContainer.getPaddingTop();
                }
            }

            int wholeHeaderDiffY = (int) event.getY() - mWholeHeaderDownY;
            if (!isWholeHeaderViewCompleteInvisible() || (wholeHeaderDiffY > 0 && shouldHandleRefresh() && !isCustomHeaderViewCompleteVisible())) {

                int paddingTop = mWholeHeaderViewDownPaddingTop + wholeHeaderDiffY;
                if (paddingTop < mMinWholeHeaderViewPaddingTop - mCustomHeaderView.getMeasuredHeight()) {
                    paddingTop = mMinWholeHeaderViewPaddingTop - mCustomHeaderView.getMeasuredHeight();
                }
                mWholeHeaderViewContainer.setPadding(0, paddingTop, 0, 0);

                return true;
            }
        }

        return false;
    }

    /**
     * 隐藏下拉刷新控件，带动画
     */
    private void hiddenRefreshHeaderView() {
        ValueAnimator animator = ValueAnimator.ofInt(mWholeHeaderViewContainer.getPaddingTop(), mMinWholeHeaderViewPaddingTop);
        animator.setDuration(mRefreshViewHolder.getTopAnimDuration());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int paddingTop = (int) animation.getAnimatedValue();
                mWholeHeaderViewContainer.setPadding(0, paddingTop, 0, 0);
            }
        });
        animator.start();
    }

    /**
     * 处理下拉刷新控件状态变化
     */
    private void handleRefreshStatusChanged() {
        switch (mCurrentRefreshStatus) {
            case IDLE:
                mRefreshViewHolder.changeToIdle();
                break;
            case PULL_DOWN:
                mRefreshViewHolder.changeToPullDown();
                break;
            case RELEASE_REFRESH:
                mRefreshViewHolder.changeToReleaseRefresh();
                break;
            case REFRESHING:
                mRefreshViewHolder.changeToRefreshing();
                break;
            default:
                break;
        }
    }

    /**
     * +++++++++++++++++++++++++++++++++++++++++++ 上面是下拉刷新，上拉加载更多的处理 +++++++++++++++++++++++++++++++++++++++++++++++++
     */

    /**
     * 初始化下拉刷新控件
     *
     * @return
     */
    private void initRefreshHeaderView() {
        mRefreshHeaderView = mRefreshViewHolder.getRefreshView();
        if (mRefreshHeaderView != null) {
            mRefreshHeaderView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            mRefreshHeaderViewHeight = mRefreshViewHolder.getRefreshHeaderViewHeight();
            
            mMinWholeHeaderViewPaddingTop = -mRefreshHeaderViewHeight;
            mMaxWholeHeaderViewPaddingTop = (int) (mRefreshHeaderViewHeight * mRefreshViewHolder.getSpringDistanceScale());

            mWholeHeaderViewContainer.setPadding(0, mMinWholeHeaderViewPaddingTop, 0, 0);
            mWholeHeaderViewContainer.addView(mRefreshHeaderView, 0);
        }
    }

    /**
     * 初始化上拉加载更多控件
     */
    private void initLoadMoreFooterView() {
        mLoadMoreFooterView = mRefreshViewHolder.getLoadingMoreView();
        if (mLoadMoreFooterView != null) {
            // 测量上拉加载更多控件的高度
            mLoadMoreFooterView.measure(0, 0);
            mLoadMoreFooterViewHeight = mLoadMoreFooterView.getMeasuredHeight();
            mLoadMoreFooterView.setVisibility(GONE);
        }
    }

    /**
     * 为listview设置滚动监听，判断什么时候执行加载更多动作
     */
    private void setAbsListViewOnScrollListener() {
        if (mAbsListView != null) {
            try {
                // 通过反射获取开发者自定义的滚动监听器，并将其替换成自己的滚动监听器，触发滚动时也要通知开发者自定义的滚动监听器（非侵入式，不让开发者继承特定的控件）
                // mAbsListView.getClass().getDeclaredField("mOnScrollListener")获取不到mOnScrollListener，必须通过AbsListView.class.getDeclaredField("mOnScrollListener")获取
                Field field = AbsListView.class.getDeclaredField("mOnScrollListener");
                field.setAccessible(true);
                // 开发者自定义的滚动监听器
                final AbsListView.OnScrollListener onScrollListener = (AbsListView.OnScrollListener) field.get(mAbsListView);
                mAbsListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                        if ((scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING) && shouldHandleAbsListViewLoadingMore(mAbsListView)) {
                            beginLoadingMore();
                        }
                        if (onScrollListener != null) {
                            onScrollListener.onScrollStateChanged(absListView, scrollState);
                        }
                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        if (onScrollListener != null) {
                            onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 为recycler设置滚动监听，判断什么时候执行加载更多动作
     */
    private void setRecyclerViewOnScrollListener() {
        if (mRecyclerView != null) {
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if ((newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_SETTLING) && shouldHandleRecyclerViewLoadingMore(mRecyclerView)) {
                        beginLoadingMore();
                    }
                }
            });
        }
    }

    /**
     * 是否满足处理上拉加载更多的条件
     */
    private boolean shouldHandleLoadingMore() {
        if (mIsLoadingMore || mCurrentRefreshStatus == RefreshStatus.REFRESHING || mLoadMoreFooterView == null || mDelegate == null) {
            return false;
        }

        // 内容是普通控件，满足
        if (mNormalView != null) {
            return true;
        }

        if (ScrollingUtil.isWebViewToBottom(mWebView)) {
            return true;
        }

        if (ScrollingUtil.isScrollViewToBottom(mScrollView)) {
            return true;
        }

        if (mAbsListView != null) {
            return shouldHandleAbsListViewLoadingMore(mAbsListView);
        }

        if (mRecyclerView != null) {
            return shouldHandleRecyclerViewLoadingMore(mRecyclerView);
        }
        //TODO
//        if (mStickyNavLayout != null) {
//            return mStickyNavLayout.shouldHandleLoadingMore();
//        }

        return false;
    }

    /**
     * 是否满足处理下拉刷新的条件
     *
     * @return
     */
    private boolean shouldHandleRefresh() {
        if (mIsLoadingMore || mCurrentRefreshStatus == RefreshStatus.REFRESHING || mRefreshHeaderView == null || mDelegate == null) {
            return false;
        }

        // 内容是普通控件，满足
        if (mNormalView != null) {
            return true;
        }

        if (ScrollingUtil.isScrollViewOrWebViewToTop(mWebView)) {
            return true;
        }

        if (ScrollingUtil.isScrollViewOrWebViewToTop(mScrollView)) {
            return true;
        }

        if (ScrollingUtil.isAbsListViewToTop(mAbsListView)) {
            return true;
        }

        if (ScrollingUtil.isRecyclerViewToTop(mRecyclerView)) {
            return true;
        }
        //TODO
//        if (ScrollingUtil.isStickyNavLayoutToTop(mStickyNavLayout)) {
//            return true;
//        }

        return false;
    }

    /**
     * 整个头部控件是否已经完全隐藏
     *
     * @return true表示完全隐藏，false表示没有完全隐藏
     */
    private boolean isWholeHeaderViewCompleteInvisible() {
        if (mCustomHeaderView != null && mIsCustomHeaderViewScrollable) {
            // 0表示x，1表示y
            int[] location = new int[2];
            getLocationOnScreen(location);
            int mOnScreenY = location[1];

            mWholeHeaderViewContainer.getLocationOnScreen(location);
            int wholeHeaderViewOnScreenY = location[1];
            if (wholeHeaderViewOnScreenY + mWholeHeaderViewContainer.getMeasuredHeight() <= mOnScreenY) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 自定义头部控件是否已经完全显示
     *
     * @return true表示已经完全显示，false表示没有完全显示
     */
    private boolean isCustomHeaderViewCompleteVisible() {
        if (mCustomHeaderView != null) {
            // 0表示x，1表示y
            int[] location = new int[2];
            getLocationOnScreen(location);
            int mOnScreenY = location[1];

            mCustomHeaderView.getLocationOnScreen(location);
            int customHeaderViewOnScreenY = location[1];
            if (mOnScreenY <= customHeaderViewOnScreenY) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 设置下拉刷新控件的paddingTop到0，带动画
     */
    private void changeRefreshHeaderViewToZero() {
        ValueAnimator animator = ValueAnimator.ofInt(mWholeHeaderViewContainer.getPaddingTop(), 0);
        animator.setDuration(mRefreshViewHolder.getTopAnimDuration());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int paddingTop = (int) animation.getAnimatedValue();
                mWholeHeaderViewContainer.setPadding(0, paddingTop, 0, 0);
            }
        });
        animator.start();
    }

    /**
     * 显示上拉加载更多控件
     */
    private void showLoadingMoreView() {
        mRefreshViewHolder.changeToLoadingMore();
        mLoadMoreFooterView.setVisibility(VISIBLE);

        ScrollingUtil.scrollToBottom(mScrollView);
        ScrollingUtil.scrollToBottom(mRecyclerView);
        ScrollingUtil.scrollToBottom(mAbsListView);
        //TODO
//        if (mStickyNavLayout != null) {
//            mStickyNavLayout.scrollToBottom();
//        }
    }

    /**
     * ===================================== 外部调用方法 =================================
     */

    /**
     * 上拉到底部时，是否显示上拉记载更多view
     */
    public void setIsShowLoadingMoreView(boolean isShowLoadingMoreView) {
        mIsShowLoadingMoreView = isShowLoadingMoreView;
    }

    public void setDelegate(RefreshLayoutDelegate delegate) {
        mDelegate = delegate;
    }

    /**
     * 设置refreshViewHolder，该方法中初始化refreshHeaderView 和 loadMoreFooterView
     */
    public void setRefreshViewHolder(BaseRefreshViewHolder refreshViewHolder) {
        mRefreshViewHolder = refreshViewHolder;
        mRefreshViewHolder.setRefreshLayout(this);
        initRefreshHeaderView();
        initLoadMoreFooterView();
    }

    /**
     * 设置下拉刷新控件下方的自定义控件
     *
     * @param customHeaderView 下拉刷新控件下方的自定义控件
     * @param scrollable       是否可以滚动
     */
    public void setCustomHeaderView(View customHeaderView, boolean scrollable) {
        if (mCustomHeaderView != null && mCustomHeaderView.getParent() != null) {
            ViewGroup parent = (ViewGroup) mCustomHeaderView.getParent();
            parent.removeView(mCustomHeaderView);
        }
        mCustomHeaderView = customHeaderView;
        if (mCustomHeaderView != null) {
            mCustomHeaderView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            mWholeHeaderViewContainer.addView(mCustomHeaderView);
            mIsCustomHeaderViewScrollable = scrollable;
        }
    }

    /**
     * refreshview回弹的效果
     */
    public void startChangeWholeHeaderViewPaddingTop(int distance) {
        //通过不断的改变paddingTop的值来达到回弹动画
        ValueAnimator animator = ValueAnimator.ofInt(mWholeHeaderViewContainer.getPaddingTop(), mWholeHeaderViewContainer.getPaddingTop() - distance);
        animator.setDuration(mRefreshViewHolder.getTopAnimDuration());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int paddingTop = (int) animation.getAnimatedValue();
                mWholeHeaderViewContainer.setPadding(0, paddingTop, 0, 0);
            }
        });
        animator.start();
    }

    /**
     * 判断recyclerView是否可以上拉加载
     */
    public boolean shouldHandleRecyclerViewLoadingMore(RecyclerView recyclerView) {
        if (mIsLoadingMore || mCurrentRefreshStatus == RefreshStatus.REFRESHING || mLoadMoreFooterView == null || mDelegate == null || recyclerView.getAdapter() == null || recyclerView.getAdapter().getItemCount() == 0) {
            return false;
        }

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager == null || manager.getItemCount() == 0) {
            return false;
        }

        if (manager instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) manager;
            if (layoutManager.findLastCompletelyVisibleItemPosition() == recyclerView.getAdapter().getItemCount() - 1) {
                return true;
            }
        } else if (manager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) manager;

            int[] out = layoutManager.findLastCompletelyVisibleItemPositions(null);
            int lastPosition = layoutManager.getItemCount() - 1;
            for (int position : out) {
                if (position == lastPosition) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断listview是否可以上拉加载
     */
    public boolean shouldHandleAbsListViewLoadingMore(AbsListView absListView) {
        if (mIsLoadingMore || mCurrentRefreshStatus == RefreshStatus.REFRESHING || mLoadMoreFooterView == null || mDelegate == null || absListView == null || absListView.getAdapter() == null || absListView.getAdapter().getCount() == 0) {
            return false;
        }

        int lastChildBottom = 0;
        if (absListView.getChildCount() > 0) {
            // 如果AdapterView的子控件数量不为0，获取最后一个子控件的bottom
            lastChildBottom = absListView.getChildAt(absListView.getChildCount() - 1).getBottom();
        }
        return absListView.getLastVisiblePosition() == absListView.getAdapter().getCount() - 1 && lastChildBottom <= absListView.getMeasuredHeight();
    }

    /**
     * 开始上拉加载更多，会触发delegate的onRefreshLayoutBeginRefreshing方法
     */
    public void beginLoadingMore() {
        if (!mIsLoadingMore && mLoadMoreFooterView != null && mDelegate != null && mDelegate.onRefreshLayoutBeginLoadingMore(this)) {
            mIsLoadingMore = true;

            if (mIsShowLoadingMoreView) {
                showLoadingMoreView();
            }
        }
    }

    /**
     * 切换到正在刷新状态，会调用delegate的onBGARefreshLayoutBeginRefreshing方法
     */
    public void beginRefreshing() {
        if (mCurrentRefreshStatus != RefreshStatus.REFRESHING && mDelegate != null) {
            mCurrentRefreshStatus = RefreshStatus.REFRESHING;
            changeRefreshHeaderViewToZero();
            handleRefreshStatusChanged();
            mDelegate.onRefreshLayoutBeginRefreshing(this);
        }
    }

    /**
     * 结束上拉加载更多
     */
    public void endLoadingMore() {
        if (mIsLoadingMore) {
            if (mIsShowLoadingMoreView) {
                // 避免WiFi环境下请求数据太快，加载更多控件一闪而过
                mHandler.postDelayed(mDelayHiddenLoadingMoreViewTask, 300);
            } else {
                mIsLoadingMore = false;
            }
        }
    }

    private Runnable mDelayHiddenLoadingMoreViewTask = new Runnable() {
        @Override
        public void run() {
            mIsLoadingMore = false;
            mRefreshViewHolder.onEndLoadingMore();
            mLoadMoreFooterView.setVisibility(GONE);
        }
    };


}
