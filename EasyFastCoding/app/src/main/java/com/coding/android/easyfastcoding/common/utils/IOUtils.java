package com.coding.android.easyfastcoding.common.utils;

import java.io.Closeable;
import java.io.IOException;
/**
 * Created by 杨强彪 on 2015/11/10.
 *
 * @描述： 关闭流方法封装
 */
public class IOUtils {
	/** 关闭流 */
	public static boolean close(Closeable io) {
		if (io != null) {
			try {
				io.close();
			} catch (IOException e) {
				LogUtils.e(e);
			}
		}
		return true;
	}
}
