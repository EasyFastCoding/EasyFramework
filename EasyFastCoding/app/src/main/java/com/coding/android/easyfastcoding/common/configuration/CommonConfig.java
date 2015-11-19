package com.coding.android.easyfastcoding.common.configuration;

import com.coding.android.easyfastcoding.common.utils.FileUtils;

/**
 * Created by 杨强彪 on 2015/11/10.
 *
 * @描述： 公用常量配置管理类
 */
public interface CommonConfig {

    int PAGER_SIZE = 20;                                   // 分页，每页的数量
    int DEBUGLEVEL = 6;                                    // 日志过滤级别

    // 获取内存缓存的大小
    int MEMORY_CACHE_SIZE = (int) (Runtime.getRuntime().maxMemory() / 1024);
    int DISK_CACHE_SIZE = 50 * 1024 * 1024;                // 图片磁盘缓存50M
    String DISK_CACHE_PATH = FileUtils.getCacheDir();      // 图片磁盘缓存路径

    /**------------------------------- 网络请求的url部分 ----------------------------------begin */
    String DEMO_STRING_REQUEST_URL = "????????????????????";


    /**------------------------------- 网络请求的url部分 ----------------------------------end */
}
