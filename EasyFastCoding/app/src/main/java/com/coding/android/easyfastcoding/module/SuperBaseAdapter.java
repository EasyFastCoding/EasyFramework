package com.coding.android.easyfastcoding.module;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import java.util.List;

/**
 * Created by 杨强彪 on 2015/10/26.
 *
 * @描述：Adapter的超级基类
 */
public abstract class SuperBaseAdapter<T> extends BaseAdapter {

    // 标记不能断层，必须从0开始
    private static final int TYPE_NORMAL = 0;

    private List<T> mDatas;
    private Context mContext;


    public SuperBaseAdapter(List<T> datas, Context context) {
        this.mDatas = datas;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (mDatas != null && mDatas.size() !=0) {
            return mDatas.size();
        }
        return 1;
    }

    @Override
    public Object getItem(int position) {
        if (mDatas != null) {
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 获得position对应的item的类型
    @Override
    public int getItemViewType(int position) {

        // 普通类型
        return getNormalItemViewType(position);
    }

    /**
     * 复写此方法添加更多类型的type
     */
    public int getNormalItemViewType(int position) {
        return TYPE_NORMAL;
    }

    // listView的item显示的种类有几种
    @Override
    public int getViewTypeCount() {

        return super.getViewTypeCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // ###1. 加载View ##########################
        if (mDatas != null && mDatas.size() == 0) {
            // 用什么样的空界面基类不知道，交给子类实现；
            return getEmptyLayout(convertView,parent);
        }

        // item_choumei_bang_empty的rootview为RelativeLayout，以此区分当实际有内容时的LinearLayout
        if (convertView instanceof RelativeLayout) {
            convertView = null;
        }


        BaseModule holder = null;
        if (convertView == null) {
            // 没有复用
            // 1. 创建holder
            holder = getItemHolder(position);// 去实现某一个holders
            // 2. 加载布局（TODO:）
            convertView = holder.getRootView();
            // 3. 设置标记
            convertView.setTag(holder);

        } else {
            // 复用
            holder = (BaseModule) convertView.getTag();
        }
        // ###########################################

        // #######2.给view加载数据###################

        // 获取数据
        T data = mDatas.get(position);
        // 给view设置数据
        holder.setData(data);
        // ########################################

        return convertView;
    }

    protected abstract BaseModule<T> getItemHolder(int position);
    /**空界面*/
    protected abstract View getEmptyLayout(View convertView,ViewGroup parent);


}
