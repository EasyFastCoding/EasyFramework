package com.coding.android.easyfastcoding.demo.banner;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.coding.android.easyfastcoding.R;
import com.coding.android.easyfastcoding.datafactory.ListDate;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import easyfastcode.library.imageload.glide.common.GlideImageLoader;
import easyfastcode.library.widget.banner.CoolBanner;

/**
 * Created by cy on 2015/12/16.
 */
public class BannerFragment extends Fragment {

    @Bind(R.id.banner1)
    CoolBanner banner1;
    @Bind(R.id.banner2)
    CoolBanner banner2;

    private LayoutInflater mInflater;

    public static BannerFragment initFragment() {
        return new BannerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View center = inflater.inflate(R.layout.fragment_banner_demo, null);
        ButterKnife.bind(this, center);

        mInflater = inflater;
        initView();
        return center;
    }

    private void initView() {
        banner1.setViews(getViews(6));
        banner2.setViews(getViews(3));
    }

    private List<View> getViews(int count) {
        List<View> views = new ArrayList<>();
        List<String> urlList = ListDate.getImageURLs(6);
        for (int i = 0; i < count; i++) {
            ImageView img = (ImageView) mInflater.inflate(R.layout.view_image, null);
            GlideImageLoader.display(getContext(), urlList.get(i), img);
            views.add(img);
        }
        return views;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
