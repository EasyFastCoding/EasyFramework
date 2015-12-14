package com.coding.android.easyfastcoding.net;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;
import easyfastcode.library.utils.LogUtils;

/**
 * Created by 杨强彪 on 2015/11/18.
 *
 * @描述：图片缓存类
 */
public class MyImageCache implements ImageLoader.ImageCache {
    LruCache <String, Bitmap> mLruCache;
    // 存储结构/容器/集合缓存的最大值
    // 1.告知缓存的具体大小
    private int maxSize = 5 * 1024 * 1024;    // 5242880 byte
    // private int maxSize = 5; // 5m

    public MyImageCache() {
        mLruCache = new LruCache<String, Bitmap>(maxSize) {
            // 2.覆写sizeOf方法
            @Override
            protected int sizeOf(String key, Bitmap value) {
                // 返回每一个entry对应的大小
                // 具体大小需要和我们定义的maxsize单位统一
                // return value.getByteCount();
                return value.getByteCount();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {// 取图片
        LogUtils.i("去图片");
        return mLruCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {// 存图片
        LogUtils.i("存图片");
        mLruCache.put(url, bitmap);
    }


}
