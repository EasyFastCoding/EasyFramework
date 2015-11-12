package com.coding.android.easyfastcoding.imageload.glide.common;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by cy on 2015/11/10.
 */
public class GlideImageLoader {

    public static void didplay(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).centerCrop().into(imageView);
    }

    public static void display(Context context, String url, ImageView imageView, int holderResourceId) {
        Glide.with(context).load(url).placeholder(holderResourceId).centerCrop().into(imageView);
    }


}
