package com.coding.android.easyfastcoding;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
/**
 * Created by 杨强彪 on 2015/11/10.
 *
 * @描述： 全局类
 */

public class BaseApplication extends Application
{
	public static Thread	mMainThread;
	public static int		mMainThreadId;
	public static Looper	mLooper;
	public static Handler	mMainHandler;
	public static Context	mContext;

	@Override
	public void onCreate()
	{
		super.onCreate();
		mContext = this;

		// 主线程
		mMainThread = Thread.currentThread();

		// 主线程的id
		// long id = mMainThread.getId();
		mMainThreadId = android.os.Process.myTid();

		mLooper = getMainLooper();

		mMainHandler = new Handler();

	}

	public static Context getContext() {
		return mContext;
	}

	public static long getMainTreadId() {
		return mMainThreadId;
	}

	public static Handler getHandler() {
		return mMainHandler;
	}
}
