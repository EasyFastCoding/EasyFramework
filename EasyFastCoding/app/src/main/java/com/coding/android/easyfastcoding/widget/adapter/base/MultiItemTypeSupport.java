package com.coding.android.easyfastcoding.widget.adapter.base;

/**
 * Created by Administrator on 2015/11/28 0028.
 */
public interface MultiItemTypeSupport<T> {

    int getLayoutId(int position, T t);

    int getViewTypeCount();

    int getItemViewType(int position, T t);

}
