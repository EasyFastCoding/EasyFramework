package com.coding.android.easyfastcoding.imageload;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by cy on 2015/11/10.
 */
public interface ImageLoader {

    public void didplay(Context context, String url, ImageView imageView);

    public void display(Context context, String url, ImageView imageView, int holderResourceId);


}
