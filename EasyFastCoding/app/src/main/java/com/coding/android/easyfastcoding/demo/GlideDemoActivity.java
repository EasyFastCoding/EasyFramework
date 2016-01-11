package com.coding.android.easyfastcoding.demo;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.coding.android.easyfastcoding.R;
import com.coding.android.easyfastcoding.common.configuration.CommonConfig;
import com.coding.android.easyfastcoding.view.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import easyfastcode.library.imageload.glide.common.GlideImageLoader;

/**
 * Created by cy on 2015/11/10.
 */
public class GlideDemoActivity extends BaseActivity {

    @Bind(R.id.iv)
    ImageView iv;
    private String url = CommonConfig.IMAGE_URL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initView();
    }


    private void init() {
        // 注册EventBus
        EventBus.getDefault().register(this);
    }

    private void initView() {
        initToolbar();
        setContentView(R.layout.ef_activity_glide);
        ButterKnife.bind(this);
        GlideImageLoader.circleImage(this, url, iv);

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

    



    public void test(Context context, int imageId, ImageView imageView) {
        GlideImageLoader.circleImage(context, imageId, imageView);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 反注册
        EventBus.getDefault().unregister(this);
    }
}
