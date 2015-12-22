package com.coding.android.easyfastcoding.demo.draglayout;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.coding.android.easyfastcoding.R;
import com.coding.android.easyfastcoding.common.configuration.CommonConfig;
import com.coding.android.easyfastcoding.view.activity.BaseActivity;
import com.coding.android.easyfastcoding.view.fragment.MainFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import easyfastcode.library.imageload.glide.common.GlideImageLoader;
import easyfastcode.library.manager.ui.SystemBarTintManager;
import easyfastcode.library.widget.draglayout.DragLayout;

/**
 * Created by 杨强彪 on 2015/12/15.
 *
 * @描述：模仿 QQ 5.0 拖拽效果（侧啦效果）
 */
public class DragLayoutDemoActivity extends AppCompatActivity {

    @Bind(R.id.fl_left_layout)
    FrameLayout left_container;
    @Bind(R.id.fl_right_layout)
    FrameLayout right_container;
    @Bind(R.id.fl_middle_layout)
    FrameLayout middle_container;
    @Bind(R.id.main_drag_layout)
    DragLayout dragLayout;
    public FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drag_demo);
        ButterKnife.bind(this);

        showSystemBarColor(R.color.green_1);

        // 可以打开左边和右边
        dragLayout.setCanOpenView(true, true);
        // 加载左边布局
        View left_holder = View.inflate(this, R.layout.left_holder, null);
        ImageView left_header = (ImageView) left_holder.findViewById(R.id.left_iv_icon);
        GlideImageLoader.circleImage(this, CommonConfig.IMAGE_URL, left_header);
        left_container.addView(left_holder);

        // 加载右边布局
        View right_holder = View.inflate(this, R.layout.right_holder, null);
        right_container.addView(right_holder);

        // 加载中间布局 , 用fragment操作
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fl_middle_layout, new MainFragment(), R.id.fl_middle_layout + "");
        ft.commit();
    }

    /**
     * 设置系统通知栏的颜色
     */
    protected void showSystemBarColor(int ColorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(ColorRes);//通知栏所需颜色
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    /**
     * 获取dragLayout实例
     */
    public DragLayout getDragLayout() {
        return dragLayout;
    }
}
