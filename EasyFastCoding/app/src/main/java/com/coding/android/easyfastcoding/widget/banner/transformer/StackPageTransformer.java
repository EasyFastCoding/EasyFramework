package com.coding.android.easyfastcoding.widget.banner.transformer;

import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/6/19 上午8:41
 * 描述:
 */
public class StackPageTransformer extends BGAPageTransformer {

    @Override
    public void handleInvisiblePage(View view, float position) {
    }

    @Override
    public void handleLeftPage(View view, float position) {
    }

    @Override
    public void handleRightPage(View view, float position) {
        ViewHelper.setTranslationX(view, -view.getWidth() * position);
    }

}