package com.coding.android.easyfastcoding.demo.net.okhttp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.coding.android.easyfastcoding.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 杨强彪 on 2016/1/4.
 *
 * @描述：
 */
public class okHttpFragmentDemo1 extends Fragment implements View.OnClickListener {

    @Bind(R.id.bt1)
    Button bt1;
    @Bind(R.id.bt2)
    Button bt2;
    @Bind(R.id.bt3)
    Button bt3;
    @Bind(R.id.bt4)
    Button bt4;
    @Bind(R.id.bt5)
    Button bt5;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View center = inflater.inflate(R.layout.fragment_coordinator_demo2, null);
        ButterKnife.bind(this, center);
        context = getActivity();
        initEvent();
        return center;
    }

    private void initEvent() {
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt1:
                RequestList.requestGet(context);
                break;
            case R.id.bt2:
                RequestList.requestPost(context);

                break;
            case R.id.bt3:
                RequestList.requestPostGson(context);

                break;
            case R.id.bt4:

                break;
            case R.id.bt5:

                break;

            default:
                break;
        }
    }
}
