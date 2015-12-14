package com.coding.android.easyfastcoding.demo.quickadapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.coding.android.easyfastcoding.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import easyfastcode.library.widget.adapter.QuickAdapter;
import easyfastcode.library.widget.adapter.base.BaseAdapterHelper;

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

        lv.setAdapter(new QuickAdapter<QuickBean>(QuickAdapterActivity.this, R.layout.item_quick_adapter_test, quickData) {
            @Override
            public View createNoDataView() {
                return null;
            }

            @Override
            protected void convert(BaseAdapterHelper helper, QuickBean item) {
                helper.setText(R.id.tv_quick_adpeter, item.getInfo());
            }
        });
    }

    private void init() {
//        quickData = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            quickData.add(new QuickBean("http://pic.nipic.com/2007-11-09/200711912453162_2.jpg", "测试"));
//        }
    }

}
