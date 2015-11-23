package com.coding.android.easyfastcoding.imageload.glide.demo;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.coding.android.easyfastcoding.R;
import com.coding.android.easyfastcoding.common.utils.LogUtils;
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
    private String url = "http://b.hiphotos.baidu.com/image/pic/item/b21bb051f81986188ff38b8448ed2e738bd4e67e.jpg";

    @Override
    protected View getTopView() {
        View titleView = getLayoutInflater().inflate(R.layout.view_title, null);
        initTitleView(titleView);
        LogUtils.i("获取topView");
        return titleView;
    }

    @Override
    protected View getCenterView() {
        // 如果需要带toolbar的activity注解不能用Glide自带的，否则。toolbar设置不上去。
        View centerView = getLayoutInflater().inflate(R.layout.ef_activity_glide, null);
        ButterKnife.bind(this, centerView);
        GlideImageLoader.circleImage(this, url, iv);
        init();
        initEvent();
        return centerView;
    }

    private void init() {
        // 注册EventBus
        EventBus.getDefault().register(this);
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

    private void initTitleView(View titleView) {
        TextView tv_title = (TextView) titleView.findViewById(R.id.tv_title);
        tv_title.setText("Toolbar");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 反注册
        EventBus.getDefault().unregister(this);
    }
}
