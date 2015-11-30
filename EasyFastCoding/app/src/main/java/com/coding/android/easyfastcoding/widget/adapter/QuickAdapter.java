package com.coding.android.easyfastcoding.widget.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.coding.android.easyfastcoding.widget.adapter.base.BaseAdapterHelper;
import com.coding.android.easyfastcoding.widget.adapter.base.BaseQuickAdapter;
import com.coding.android.easyfastcoding.widget.adapter.base.MultiItemTypeSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cy on 2015/11/28 0028.
 */
public abstract class QuickAdapter<T> extends BaseQuickAdapter<T, BaseAdapterHelper> {

    public QuickAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public QuickAdapter(Context context, int layoutResId, List<T> data) {
        super(context, layoutResId, data);
    }

    public QuickAdapter(Context context, ArrayList<T> data, MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(context, data, multiItemTypeSupport);
    }


    @Override
    protected BaseAdapterHelper getAdapterHelper(int position, View convertView, ViewGroup parent) {

        if (mMultiItemSupport != null) {
            return BaseAdapterHelper.get(
                    context,
                    convertView,
                    parent,
                    mMultiItemSupport.getLayoutId(position, data.get(position)),
                    position);
        } else {
            return BaseAdapterHelper.get(context, convertView, parent, layoutResId, position);
        }
    }
}
