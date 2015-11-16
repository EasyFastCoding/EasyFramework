package com.coding.android.easyfastcoding.imageload.glide.common;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;
import com.coding.android.easyfastcoding.common.configuration.CommonConfig;

/**
 *
 * 不想使用glide的默认配置，需要自己配置缓存及图片加载的质量，需要在AndroidManifest.xml中添加如下配置。
   <meta-data
     android:name="com.mypackage.MyGlideModule"
     android:value="GlideModule" />
 *
 * Created by cy on 2015/11/10.
 */
public class GlideSettingModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //设置图片的质量
        //builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        //配置磁盘缓存
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, CommonConfig.DISK_CACHE_PATH, CommonConfig.DISK_CACHE_SIZE));
        //配置内存缓存
        builder.setMemoryCache(new LruResourceCache(CommonConfig.MEMORY_CACHE_SIZE));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }


}
