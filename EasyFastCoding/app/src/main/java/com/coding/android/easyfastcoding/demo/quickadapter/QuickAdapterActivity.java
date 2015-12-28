package com.coding.android.easyfastcoding.demo.quickadapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.coding.android.easyfastcoding.R;
import com.coding.android.easyfastcoding.datafactory.model.QuickBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import easyfastcode.library.widget.adapter.QuickAdapter;
import easyfastcode.library.widget.adapter.ViewHelper;

public class QuickAdapterActivity extends Activity {

    @Bind(R.id.lv)
    ListView lv;

    private List<QuickBean> quickData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_adapter);
        ButterKnife.bind(this);
        init();
        test1();
    }

    private void init() {
        quickData = new ArrayList<>();
        for (int i = 0; i < 0; i++) {
            quickData.add(new QuickBean("http://pic.nipic.com/2007-11-09/200711912453162_2.jpg", "测试"));
        }
    }

    private void test1() {
        QuickAdapter adapter = new QuickAdapter<QuickBean>(R.layout.item_quick_adapter_test, quickData) {
            @Override
            public View createNoDataView() {
                return null;
            }

            @Override
            protected void onBindData(Context context, int position, QuickBean item, int itemLayoutId, ViewHelper helper) {
                helper.setText(R.id.tv_quick_adpeter, item.getInfo());
            }
        };
        lv.setAdapter(adapter);
    }

//    private void test2() {
//        lv.setAdapter(new QuickAdapter<QuickBean>(QuickAdapterActivity.this, R.layout.item_quick_adapter_test, quickData) {
//            @Override
//            public View createNoDataView() {
//                return null;
//            }
//
//            @Override
//            protected void convert(BaseAdapterHelper helper, QuickBean item) {
//                helper.setText(R.id.tv_quick_adpeter, item.getInfo());
//            }
//        });
//    }

}
