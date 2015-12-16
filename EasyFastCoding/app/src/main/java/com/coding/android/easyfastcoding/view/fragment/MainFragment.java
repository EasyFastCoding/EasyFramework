package com.coding.android.easyfastcoding.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coding.android.easyfastcoding.R;
import com.coding.android.easyfastcoding.demo.draglayout.DragLayoutDemoActivity;
import com.coding.android.easyfastcoding.view.adapter.FragmentTabAdapter;
import com.coding.android.easyfastcoding.view.fragment.main.ChatFragment;
import com.coding.android.easyfastcoding.view.fragment.main.ContactFragment;
import com.coding.android.easyfastcoding.view.fragment.main.FriendFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import easyfastcode.library.widget.customViewpager.NoScrollViewPager;

/**
 * Created by 杨强彪 on 2015/12/15.
 *
 * @描述：
 */
public class MainFragment extends Fragment implements ViewPager.OnPageChangeListener {

    @Bind(R.id.vp_pager)
    NoScrollViewPager vpPager;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolBar;
    @Bind(R.id.toolbar_title)
    TextView mToolBarTitle;
    @Bind(R.id.tv_right)
    TextView mTextRight;
    private DragLayoutDemoActivity context;
    private List<String> list_title;
    private List<Fragment> list_fragment;
    private FragmentTabAdapter fAdapter;
    private int[] tabImg;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main, container, false);
        ButterKnife.bind(this, view);
        context = (DragLayoutDemoActivity) getActivity();
        initToolbar();
        initControls(view);
        fragmentChange();
        return view;

    }

    private void initToolbar() {
        // 设置返回图标
        mToolBar.setNavigationIcon(R.drawable.ef_selector_back_icon);
        context.setSupportActionBar(mToolBar);
        context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.getDragLayout().openLeft();
            }
        });
        mTextRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.getDragLayout().openRight();
            }
        });

        if (mToolBarTitle != null) {
            context.getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }


    private void initControls(View view) {

        vpPager.addOnPageChangeListener(this);
        //为tabLayout上的图标赋值
        tabImg = new int[]{R.drawable.selector_tab_chat, R.drawable.selector_tab_contacts, R.drawable
                .selector_tab_friend};
        // 名称赋值
        list_title = new ArrayList<>();
        list_title.add("微信");
        list_title.add("联系人");
        list_title.add("朋友圈");
    }

    /**
     * 采用viewpager中切换fragment
     */
    private void fragmentChange() {
        list_fragment = new ArrayList<>();
        list_fragment.add(new ChatFragment());
        list_fragment.add(new ContactFragment());
        list_fragment.add(new FriendFragment());
        fAdapter = new FragmentTabAdapter(context.getSupportFragmentManager(), list_fragment, list_title, tabImg);
        vpPager.setAdapter(fAdapter);
        //将tabLayout与viewpager连起来
        tabLayout.setupWithViewPager(vpPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            View customTabView = fAdapter.getCustomTabView(context, i);
            tabLayout.getTabAt(i).setCustomView(customTabView);
        }
        tabLayout.setPadding(0, 10, 0, 10);
        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);
        // 初始化选中第一个tab(保证第一个tab显示是被选中状态)
        vpPager.setCurrentItem(1);
        vpPager.setCurrentItem(0);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // 添加这个监听就是为了设置toolbar标题切换
        mToolBarTitle.setText(list_title.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
