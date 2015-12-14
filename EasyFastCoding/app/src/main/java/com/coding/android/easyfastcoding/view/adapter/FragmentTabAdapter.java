package com.coding.android.easyfastcoding.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.coding.android.easyfastcoding.R;
import easyfastcode.library.utils.LogUtils;

import java.util.List;

/**
 * Created by 杨强彪 on 2015/11/28.
 *
 * @描述：
 */
public class FragmentTabAdapter extends FragmentPagerAdapter {
    private List<Fragment> list_fragment;   //fragment列表
    private List<String> list_Title;        //tab名的列表
    private int [] tabImg;                  //tab名的列表

    public FragmentTabAdapter(FragmentManager fm, List<Fragment> list_fragment, List<String> list_Title, int[] tabImg) {

        super(fm);
        this.list_fragment = list_fragment;
        this.list_Title = list_Title;
        this.tabImg = tabImg;
    }

    @Override
    public Fragment getItem(int position) {
        LogUtils.i("0000position = " + position);

        return list_fragment.get(position);
    }

    @Override
    public int getCount() {
        return list_fragment.size();
    }

    /**
     * 此方法是给tablayout中的tab赋值的，就是显示名称
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {

        return list_Title.get(position % list_Title.size());

    }

    /**
     *获取自定义布局的view
     * */
    public View getCustomTabView(Context context, int position) {
        View view = View.inflate(context, R.layout.tab_item_view, null);
        TextView tv = (TextView) view.findViewById(R.id.textview);
        ImageView imageview = (ImageView) view.findViewById(R.id.imageview);
        tv.setText(list_Title.get(position));
        imageview.setImageResource(tabImg[position]);

        return view;
    }

}
