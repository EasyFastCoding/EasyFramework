package com.coding.android.easyfastcoding.imageload.glide.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.coding.android.easyfastcoding.R;
import com.coding.android.easyfastcoding.common.configuration.CommonConfig;
import com.coding.android.easyfastcoding.event.EventDemo;
import com.coding.android.easyfastcoding.imageload.glide.common.GlideImageLoader;
import com.coding.android.easyfastcoding.net.demo.NetWorkDemo;
import com.coding.android.easyfastcoding.view.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by cy on 2015/11/10.
 */
public class GlideDemoActivity extends BaseActivity {

    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.bt_start_net_demo)
    Button btStartNetDemo;
    @Bind(R.id.tv_show_event_text)
    TextView tvShowEventText;
    private String url = CommonConfig.IMAGE_URL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initView();
        initEvent();
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

    public void onEventMainThread(EventDemo event) {
        String msg = event.getMsg();
        tvShowEventText.setText(msg);
    }

    private void initEvent() {
        btStartNetDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GlideDemoActivity.this, NetWorkDemo.class);
                startActivity(intent);
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
