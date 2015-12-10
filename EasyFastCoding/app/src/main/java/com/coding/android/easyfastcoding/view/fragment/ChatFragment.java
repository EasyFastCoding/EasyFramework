package com.coding.android.easyfastcoding.view.fragment;

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
import com.coding.android.easyfastcoding.view.fragment.chat.ReadFragment;
import com.coding.android.easyfastcoding.view.fragment.chat.UnReadFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by 杨强彪 on 2015/11/28.
 *
 * @描述：
 */
public class ChatFragment extends Fragment {

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
        mTextviewArray = getResources().getStringArray(R.array.array_chat);
    }


    /**
     * 采用viewpager中切换fragment
     */
    private void fragmentChange() {
        list_fragment = new ArrayList<>();
        list_fragment.add(new ReadFragment());
        list_fragment.add(new UnReadFragment());
        // 下面这句代码一定要传getChildFragmentManager()，一级FragmentManager管理一级fragment
        // 不能用上一层的（getActivity().getSupportFragmentManager()）否则根本设置不进去
        fAdapter = new FragmentChatAdapter(getChildFragmentManager(), list_fragment, mTextviewArray);
        vpPager.setAdapter(fAdapter);
        //将tabLayout与viewpager连起来
        tabLayout.setupWithViewPager(vpPager);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
