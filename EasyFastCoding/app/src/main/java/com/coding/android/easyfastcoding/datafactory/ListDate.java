package com.coding.android.easyfastcoding.datafactory;

import com.coding.android.easyfastcoding.datafactory.model.QuickBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cy on 2015/12/14.
 */
public class ListDate {

    public static String[] urls = {
            "http://pic19.nipic.com/20120323/485395_164257185000_2.jpg",
            "http://pic15.nipic.com/20110622/6766382_171833315747_2.jpg",
            "http://pic.nipic.com/2007-11-05/20071151194427_2.jpg",
            "http://image6.360doc.com/DownloadImg/2009/12/2616/2065983_8.jpg",
            "http://pic.58pic.com/58pic/13/71/53/93h58PICGX2_1024.jpg",
            "http://e.hiphotos.baidu.com/zhidao/pic/item/0eb30f2442a7d9338c277249af4bd11373f00178.jpg",
            "http://pic43.nipic.com/20140711/9301655_160101879000_2.jpg",
            "http://pic31.nipic.com/20130710/12018626_103826765000_2.jpg",
            "http://pic49.nipic.com/file/20140928/4949133_153849953000_2.jpg",
            "http://pic49.nipic.com/file/20140928/4949133_154637763000_2.jpg"
    };

    public static List<QuickBean> getQuickBean(int length) {
        List<QuickBean> datas = new ArrayList<QuickBean>();
        for (int i = 0; i < length; i++) {
            datas.add(new QuickBean("http://pic.nipic.com/2007-11-09/200711912453162_2.jpg", "测试"));
        }
        return datas;
    }

    public static List<String> getImageURLs(int length) {
        List<String> urlList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            urlList.add(urls[i % urls.length]);
        }
        return urlList;
    }

}
