package com.coding.android.easyfastcoding.widget.adapter.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/28 0028.
 */
public abstract class BaseQuickAdapter<T, H extends BaseAdapterHelper> extends BaseAdapter {

    protected static final String TAG = BaseQuickAdapter.class.getSimpleName();
    protected final Context context;
    protected int layoutResId;
    protected final List<T> data;

    /**
     * 多布局支持类
     */
    protected MultiItemTypeSupport<T> mMultiItemSupport;

    /**
     * 不带数据的adapter
     */
    public BaseQuickAdapter(Context context, int layoutResId) {
        this(context, layoutResId, null);
    }

    /**
     * 带数据的adapter
     */
    public BaseQuickAdapter(Context context, int layoutResId, List<T> data) {
        this.data = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
        this.context = context;
        this.layoutResId = layoutResId;
    }

    /**
     * 带数据多布局的adapter
     */
    public BaseQuickAdapter(Context context, ArrayList<T> data, MultiItemTypeSupport<T> multiItemTypeSupport) {
        this.data = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
        this.context = context;
        this.mMultiItemSupport = multiItemTypeSupport;
    }

    @Override
    public int getCount() {
        int extra = data.size() > 0 ? 0 : 1;
        return data.size() + extra;
    }

    @Override
    public T getItem(int position) {
        if (position >= data.size())
            return null;
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (mMultiItemSupport != null)
            return mMultiItemSupport.getViewTypeCount() + 1;
        return 2;
    }


    @Override
    public int getItemViewType(int position) {
        if (mMultiItemSupport != null)
            return mMultiItemSupport.getItemViewType(position, data.get(position));

        return data.size() == 0 ? 0 : 1;
    }

    /**
     * 当没有数据时，
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 显示没有数据的view
        if (getItemViewType(position) == 0) {
            View noDataView = null;
            noDataView = createNoDataView();
            if (noDataView == null) {
                noDataView = new FrameLayout(context);
            }
            return noDataView;
        }
        //创建一个helper类，相当于viewholder
        final H helper = getAdapterHelper(position, convertView, parent);
        T item = getItem(position);
        helper.setAssociatedObject(item);
        convert(helper, item);
        return helper.getView();
    }

    /**
     * 没有数据时应该显示的view
     */
    public abstract View createNoDataView();

    /**
     * 用helper转换成bean对应的view
     */
    protected abstract void convert(H helper, T item);

    /**
     * 创建viewHelp类，convert的初始化就在help类中完成。
     */
    protected abstract H getAdapterHelper(int position, View convertView, ViewGroup parent);

    public void add(T elem) {
        data.add(elem);
        notifyDataSetChanged();
    }

    public void addAll(List<T> elems) {
        data.addAll(elems);
    }

    public void set(T oldElem, T newElem) {
        set(data.indexOf(oldElem), newElem);
    }

    public void set(int index, T elem) {
        data.set(index, elem);
        notifyDataSetChanged();
    }

    public void remove(T elem) {
        data.remove(elem);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        data.remove(index);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
        data.clear();
        data.addAll(elem);
        notifyDataSetChanged();
    }

    public boolean contains(T elem) {
        return data.contains(elem);
    }

    /**
     * Clear data list
     */
    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }


}
