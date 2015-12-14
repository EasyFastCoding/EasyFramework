package com.coding.android.easyfastcoding.demo.pull2refresh;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.coding.android.easyfastcoding.R;
import com.coding.android.easyfastcoding.view.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by cy on 2015/12/14.
 */
public class RefreshActivity extends BaseActivity {

    @Bind(R.id.fl_container)
    FrameLayout flContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        ButterKnife.bind(this);
    }
}
