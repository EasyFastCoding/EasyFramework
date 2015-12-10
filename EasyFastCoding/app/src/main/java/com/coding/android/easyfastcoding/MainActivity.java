package com.coding.android.easyfastcoding;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.coding.android.easyfastcoding.common.configuration.CommonConfig;
import com.coding.android.easyfastcoding.view.activity.BaseActivity;
import com.coding.android.easyfastcoding.view.adapter.FragmentTabAdapter;
import com.coding.android.easyfastcoding.view.fragment.ChatFragment;
import com.coding.android.easyfastcoding.view.fragment.ContactFragment;
import com.coding.android.easyfastcoding.view.fragment.FriendFragment;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private int[] tabImg;
    private TabLayout mTabLayout;
    private ViewPager vp_pager;
    private List<String> list_title;
    private List<Fragment> list_fragment;
    private FragmentTabAdapter fAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initToolbar();
        initControls();
        fragmentChange();
    }
    private void initToolbar() {
        setTitle(CommonConfig.GLIDE_DEMO_TITLE);

        getToolBar().setNavigationIcon(R.drawable.ef_selector_back_icon);

        getToolBar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_right);
                finish();
            }
        });
    }
    private void initControls() {

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        vp_pager = (ViewPager) findViewById(R.id.vp_pager);
        vp_pager.addOnPageChangeListener(this);
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
        fAdapter = new FragmentTabAdapter(getSupportFragmentManager(), list_fragment, list_title, tabImg);
        vp_pager.setAdapter(fAdapter);
        //将tabLayout与viewpager连起来
        mTabLayout.setupWithViewPager(vp_pager);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            View customTabView = fAdapter.getCustomTabView(this, i);
            mTabLayout.getTabAt(i).setCustomView(customTabView);
        }
        mTabLayout.setPadding(0, 20, 0, 20);
        mTabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);
        // 初始化选中第一个tab(保证第一个tab显示是被选中状态)
        vp_pager.setCurrentItem(1);
        vp_pager.setCurrentItem(0);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // 添加这个监听就是为了设置toolbar标题切换
        setTitle(list_title.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(vp_pager != null){
            vp_pager.removeOnPageChangeListener(this);
        }
    }
}
