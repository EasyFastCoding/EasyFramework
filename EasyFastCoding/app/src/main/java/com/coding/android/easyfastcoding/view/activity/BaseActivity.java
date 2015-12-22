package com.coding.android.easyfastcoding.view.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coding.android.easyfastcoding.MainActivity;
import com.coding.android.easyfastcoding.R;

import easyfastcode.library.manager.ui.SystemBarTintManager;


/**
 * 带ToolBar的基础Activity
 */
public class BaseActivity extends AppCompatActivity {
    private LinearLayout mParentView;
    private Toolbar mToolBar;
    private TextView mToolBarTitle;
    //    private Button mTextIcon;
    private TextView mTextRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ef_activity_base);
    }

    @Override
    public void setContentView(int layoutResID) {
        if (R.layout.ef_activity_base == layoutResID) {
            super.setContentView(layoutResID);

            mParentView = (LinearLayout) findViewById(R.id.base_parent_view);
            mToolBar = (Toolbar) findViewById(R.id.toolbar);
            mToolBarTitle = (TextView) findViewById(R.id.toolbar_title);
            mTextRight = (TextView) findViewById(R.id.tv_right);
            //   mTextIcon = (Button) findViewById(R.id.text_icon);

            initToolbar(mToolBar, mToolBarTitle);
            return;
        }
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mParentView.addView(getLayoutInflater().inflate(layoutResID, null), params);

    }

    @Override
    public void setContentView(View view) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mParentView.addView(view, params);
    }

    private void initToolbar(Toolbar toolbar, TextView title) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //当前不是首页时，返回按钮才有效
                if (!BaseActivity.this.getClass().equals(MainActivity.class))
                    finish();
            }
        });

        if (title != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    protected TextView setmTextRight(String rightText) {
        mTextRight.setVisibility(View.VISIBLE);
        mTextRight.setText(rightText);
        return mTextRight;
    }

    public Toolbar getToolBar() {
        return mToolBar;
    }

    public void setBackground(int colorId) {
        if (null != mParentView) {
            mParentView.setBackgroundColor(getResources().getColor(colorId));
        }
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (mToolBarTitle != null) {
            mToolBarTitle.setText(title);
        }
    }


    //    protected void setTextIcon(String text, View.OnClickListener listener) {
    //        mTextIcon.setVisibility(View.VISIBLE);
    //        mTextIcon.setText(text);
    //        mTextIcon.setOnClickListener(listener);
    //    }
}
