package com.coding.android.easyfastcoding.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.coding.android.easyfastcoding.demo.banner.BannerFragment;
import com.coding.android.easyfastcoding.demo.net.okhttp.okHttpFragmentDemo1;
import com.coding.android.easyfastcoding.demo.newparts.CoordinatorLayoutFragmentDemo1;

import java.util.List;

/**
 * Created by 杨强彪 on 2015/11/28.
 *
 * @描述：
 */
public class FragmentChatAdapter extends FragmentPagerAdapter {
    private List<Fragment> list_fragment;   //fragment列表
    private String[] array_Title;           //tab名的列表

    public FragmentChatAdapter(FragmentManager fm, List<Fragment> list_fragment, String[] array_Title) {

        super(fm);
        this.list_fragment = list_fragment;
        this.array_Title = array_Title;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 2) {
            return BannerFragment.initFragment();
        } else if (position == 3) {
            return new CoordinatorLayoutFragmentDemo1();
        }else if (position == 4) {
            return new okHttpFragmentDemo1();
        } else{
            return list_fragment.get(position);
        }
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

        return array_Title[position % array_Title.length];

    }
}
