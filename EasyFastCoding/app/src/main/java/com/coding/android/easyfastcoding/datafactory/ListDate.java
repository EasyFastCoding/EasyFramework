package com.coding.android.easyfastcoding.datafactory;

import com.coding.android.easyfastcoding.datafactory.model.QuickBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cy on 2015/12/14.
 */
public class ListDate {

    public static List<QuickBean> getQuickBean(int length) {
        List<QuickBean> datas = new ArrayList<QuickBean>();
        for (int i = 0; i < length; i++) {
            datas.add(new QuickBean("http://pic.nipic.com/2007-11-09/200711912453162_2.jpg", "测试"));
        }
        return datas;
    }

}
