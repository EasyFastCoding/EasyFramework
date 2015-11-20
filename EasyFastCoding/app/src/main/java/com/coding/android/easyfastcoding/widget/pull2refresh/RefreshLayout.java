package com.coding.android.easyfastcoding.widget.pull2refresh;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by cy on 2015/11/20.
 */
public class RefreshLayout extends LinearLayout {

    /**
     * 整个头部控件，下拉刷新控件mRefreshHeaderView和下拉刷新控件下方的自定义组件mCustomHeaderView的父控件
     */
    private LinearLayout mHeaderContainer;

    private Handler mHandler;

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
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
}
