package com.coding.android.easyfastcoding.demo.pull2refresh;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.coding.android.easyfastcoding.R;
import com.coding.android.easyfastcoding.demo.pull2refresh.fragment.NormalRefreshFragment;
import com.coding.android.easyfastcoding.view.activity.BaseActivity;

/**
 * Created by cy on 2015/12/14.
 */
public class RefreshActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        initView();
    }

    private void initView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_container, NormalRefreshFragment.initFragment());
        fragmentTransaction.commit();
    }

}
