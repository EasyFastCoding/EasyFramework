package com.coding.android.easyfastcoding.imageload.glide.demo;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.ImageView;

import com.coding.android.easyfastcoding.R;
import com.coding.android.easyfastcoding.databinding.ActivityGlideBinding;
import com.coding.android.easyfastcoding.imageload.glide.common.GlideImageLoader;

/**
 * Created by cy on 2015/11/10.
 */
public class GlideDemoActivity extends Activity {

    private String url = "http://b.hiphotos.baidu.com/image/pic/item/b21bb051f81986188ff38b8448ed2e738bd4e67e.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGlideBinding binding = DataBindingUtil.setContentView(this, R.layout.ef_activity_glide);
        GlideImageLoader.circleImage(this, url, binding.iv);
    }

    public void test(Context context, int imageId, ImageView imageView) {
        GlideImageLoader.circleImage(context, imageId, imageView);
    }

}
