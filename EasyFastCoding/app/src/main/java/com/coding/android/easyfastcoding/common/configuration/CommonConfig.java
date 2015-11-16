package com.coding.android.easyfastcoding.common.configuration;

import com.coding.android.easyfastcoding.common.utils.FileUtils;

/**
 * Created by cy on 2015/11/10.
 */
public class CommonConfig {

    /**
     * 图片磁盘缓存50M
     */
    public static int DISK_CACHE_SIZE = 50 * 1024 * 1024;
    /**
     * 图片磁盘缓存路径
     */
    public static String DISK_CACHE_PATH = FileUtils.getCacheDir();
    /**
     * 内存缓存大小
     */
    public static int MEMORY_CACHE_SIZE = (int) (Runtime.getRuntime().maxMemory() / 1024);
}
