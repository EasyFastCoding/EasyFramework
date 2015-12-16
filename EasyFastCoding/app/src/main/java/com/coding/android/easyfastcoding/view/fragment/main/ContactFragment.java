package com.coding.android.easyfastcoding.view.fragment.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coding.android.easyfastcoding.R;
import com.coding.android.easyfastcoding.view.adapter.FragmentChatAdapter;
import com.coding.android.easyfastcoding.view.fragment.main.contact.ContactChildFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by 杨强彪 on 2015/11/28.
 *
 * @描述：
 */
public class ContactFragment extends Fragment implements ViewPager.OnPageChangeListener {
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.vp_pager)
    ViewPager vpPager;
    private List<Fragment> list_fragment;
    private FragmentChatAdapter fAdapter;
    private String[] mTextviewArray;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);
        initControls();
        fragmentChange();

        return view;
    }
    private void initControls() {
        // 名称赋值
        mTextviewArray = getResources().getStringArray(R.array.array_contacts);
        // 设置tab可以滚动
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//android:showDividers="none"
        vpPager.addOnPageChangeListener(this);

    }
    /**
     * 采用viewpager中切换fragment
     */
    private void fragmentChange() {
        list_fragment = new ArrayList<>();
        for (int i = 0; i <mTextviewArray.length ; i++) {
            list_fragment.add(new ContactChildFragment());
        }
        fAdapter = new FragmentChatAdapter(getChildFragmentManager(), list_fragment, mTextviewArray);
        vpPager.setAdapter(fAdapter);
        //将tabLayout与viewpager连起来
        tabLayout.setupWithViewPager(vpPager);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if(vpPager != null){
            vpPager.removeOnPageChangeListener(this);
        }
    }

    // 如果每个fragment都有自己的内容下面监听回调部分可以不需要
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // 设置内容
        ContactChildFragment fragment = (ContactChildFragment) list_fragment.get(position);
        fragment.setTvContant(mTextviewArray[position]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
