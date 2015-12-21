package easyfastcode.library;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import easyfastcode.library.utils.sp.SpCache;

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

		// 初始化SpCache,没在这初始化用SpCache就会报异常
		SpCache.init(this);

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
