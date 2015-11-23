package com.coding.android.easyfastcoding.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coding.android.easyfastcoding.R;


/**
 * @描述： Activity 的基类
 */
public abstract class BaseActivity extends Activity {
    /**
     * 顶部title文字
     */
    public TextView title_content;
    /**
     * 左边返回键
     */
    public ImageView title_left_btn;
    /**
     * 右边按键
     */
    public ImageView title_right_btn;
    /**
     * 顶部视图
     */
    public View topView;
    /**
     * 中间视图
     */
    public View centerView;

    /** 页面 上方布局，中间视图布局 */
    protected LinearLayout top_lay, center_lay;
    protected Bundle savedInstanceState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controlTitle();
        setContentView(R.layout.activity_base_main);
        this.savedInstanceState = savedInstanceState;
        initView();
    }

    /**
     * 得到最上面的视图
     */
    protected abstract View getTopView();

    /**
     * 得到中间的视图
     */
    protected abstract View getCenterView();

    /**
     * 去除屏幕标题栏
     **/
    protected void controlTitle() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(null);
    }

    /**
     * 初始化视图
     */
    public void initView() {
        top_lay = (LinearLayout) findViewById(R.id.top_lay);
        // 页面中间布局
        center_lay = (LinearLayout) findViewById(R.id.center_lay);
        topView = getTopView();// 得到最上面的视图
        centerView = getCenterView();// 得到中间的视图
        if (topView != null) {
            top_lay.setVisibility(View.VISIBLE);
            top_lay.removeAllViews();
            top_lay.addView(topView, new ViewPager.LayoutParams());

        } else {
            top_lay.removeAllViews();
            top_lay.setVisibility(View.GONE);
        }
        if (centerView != null) {
            center_lay.removeAllViews();
            center_lay.addView(centerView, new ViewPager.LayoutParams());
        }

    }

    /**
     * 设置中间视图
     *
     * @param layout
     */
    public void setCenterView(int layout) {
        centerView = LayoutInflater.from(this).inflate(layout, null);
    }

}
