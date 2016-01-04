package com.coding.android.easyfastcoding.demo.net.okhttp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.coding.android.easyfastcoding.demo.net.bean.User;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


/**
 * Created by 杨强彪 on 2016/1/4.
 *
 * @描述： okhttp 各种请求事例代码 详细见下面网址
 *         https://github.com/hongyangAndroid/okhttp-utils
 */
public class RequestList {


    /**
     * 简单的get请求
     */
    public static void requestGet(final Context context) {
        String url = "http://www.csdn.net/";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("username", "hyman")
                .addParams("password", "123")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(context, "Get请求错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, "Get请求成功 = " + response, Toast.LENGTH_SHORT).show();
                        Log.i("okhttp", "response = " + response);
                    }
                });
    }


    /**
     * 简单的post请求
     */
    public static void requestPost(final Context context) {
        String url = "http://test-cmweb.choumei.me/v1/promotion/index.html";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", "92f9a13a81e9886f")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(context, "Post请求错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, "Post请求成功 = " + response, Toast.LENGTH_SHORT).show();
                        Log.i("okhttp", "response = " + response);
                    }
                });
    }


    /**
     * post请求提交一个gson字符串到服务器
     */
    public static void requestPostGson(final Context context) {
        String url = "http://test-cmweb.choumei.me/v1/promotion/index.html";
        OkHttpUtils
                .postString()
                .url(url)
                .content(new Gson().toJson(new User("92f9a13a81e9886f")))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(context, "PostGson请求错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, "PostGson请求成功 = " + response, Toast.LENGTH_SHORT).show();
                        Log.i("okhttp", "response = " + response);
                    }
                });
    }
}
