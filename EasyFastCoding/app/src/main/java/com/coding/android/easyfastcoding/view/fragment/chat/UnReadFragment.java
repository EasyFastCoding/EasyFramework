package com.coding.android.easyfastcoding.view.fragment.chat;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coding.android.easyfastcoding.R;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by 杨强彪 on 2015/11/28.
 *
 * @描述：未读信息
 */
public class UnReadFragment extends Fragment {

    @Bind(R.id.tv_contant)
    TextView tvContant;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_unread, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
