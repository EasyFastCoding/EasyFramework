package com.coding.android.easyfastcoding.net;

import android.content.Context;
import android.webkit.WebView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coding.android.easyfastcoding.common.utils.LogUtils;
import com.coding.android.easyfastcoding.common.utils.UIUtils;
import com.coding.android.easyfastcoding.net.request.BaseRequest;
import com.coding.android.easyfastcoding.net.request.GsonRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 杨强彪 on 2015/11/11.
 *
 * @描述： volley 网络请求帮助类
 * <p/>
 * <p/>
 * <p/>
 * <p/>
 * //网络请求的取消   结合生命周期联动使用
 * gsonRequest.cancel();//单个请求的取消
 * 请求队列设置tag
 * gsonRequest.setTag("TAG1");
 * //多个请求的取消
 * RequestQueue requestQueue = VolleyTools.getInstance(MainActivity.this).getQueue();
 * requestQueue.cancelAll(null); //tag不能为空 否则报错
 * requestQueue.cancelAll("TAG1");//取消指定请求
 */
public class VolleyHelper {


    private static VolleyHelper instance;
    private static final String TAG = "volley";
    private RequestQueue mRequestQueue;
    private String mUserAgent; //user-agent
    ImageLoader mImageLoader;
    ImageLoader.ImageCache mImageCache;

    // 2.私有化构造方法
    private VolleyHelper(Context context) {
        mRequestQueue = getRequestQueue();
        mImageCache = new MyImageCache();
        mImageLoader = new ImageLoader(mRequestQueue, mImageCache);
        //获取user-agent
        mUserAgent = new WebView(context).getSettings().getUserAgentString();
    }

    /**
     * 单例的方式获取实例对象
     */
    public static VolleyHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (VolleyHelper.class) {
                if (instance == null) {
                    instance = new VolleyHelper(context);
                }
            }
        }
        return instance;
    }


    /**
     * 获取创建的Volley的请求队列
     *
     * @return
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(UIUtils.getContext());
        }
        return mRequestQueue;
    }

    /**
     * 将Request添加到请求队列中
     *
     * @param req
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    /**
     * 图片加载器
     */
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    /**
     * 自定义内存缓存
     */
    public ImageLoader.ImageCache getImageCache() {
        return mImageCache;
    }
    //  --------------------------------------  四大网络请求 -----------------------------begin

    /**
     * jsonArray的请求
     * 返回一个jsonArray
     */
    public void jsonArrayRequest_net(String url, BaseRequest request, final CommonResponseListener<JSONArray>
            listener) {
        jsonArrayRequest(url, request, listener);
    }

    /**
     * jsonObject的请求
     * 建议 ：直接用 gsonBeanReqest_net 更合适，省去后面解析处理
     * 返回一个jsonObject
     */
    public void jsonObjectRequest_net(String url, BaseRequest request, final CommonResponseListener<JSONObject>
            listener) {
        jsonObjectRequest(url, request, listener);
    }

    /**
     * 自定义GsonObjectReqest 的具体实现
     * T :为对应的bean
     * 直接返回解析好了的 bean
     */
    public <T> void gsonBeanReqest_net(String url, BaseRequest request, Class<T> clazz, final
    CommonResponseListener<T> listener) {
        gsonBeanReqest(url, request, clazz, listener);
    }

    /**
     * 这个是通用方法，但是后面需要做点解析处理
     * 返回一个  string
     */
    public void stringRequest_net(String url, BaseRequest request, final CommonResponseListener<String> listener) {
        stringRequest(url, request, listener);
    }

    //  --------------------------------------  四大网络请求 -----------------------------end


    private void jsonArrayRequest(String url, BaseRequest request, final CommonResponseListener<JSONArray> listener) {

        Gson gson = new Gson();
        final String text = gson.toJson(request);
        LogUtils.i(TAG, "url = " + url);
        LogUtils.i(TAG, "request =" + text);

        // jsonArrayRequest构造参数可以传requestBody(String类型)进去 ，不传默认请求体是null
        JsonArrayRequest jar = new JsonArrayRequest(Request.Method.POST, url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        listener.onSucceed(response);
                    }
                }, new Response.ErrorListener() {
            //请求错误监听
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                listener.onErrorResult(null, error.getMessage(), "");
            }
        }) {
            //获取请求json参数字符串
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("code", text);
                return params;
            }

            //获取请求头信息
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        //添加到请求队列
        addToRequestQueue(jar);
    }

    private void jsonObjectRequest(String url, BaseRequest request, final CommonResponseListener<JSONObject> listener) {

        Gson gson = new Gson();
        final String text = gson.toJson(request);
        //开启日志
        LogUtils.i(TAG + "=url", url);
        LogUtils.i(TAG + "=request", text);

        // jsonObjectRequest构造参数可以传requestBody(String类型)进去 ，不传默认请求体是null
        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, url,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        listener.onSucceed(response);
                    }
                }, new Response.ErrorListener() {
            //请求错误监听
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                listener.onErrorResult(null, error.getMessage(), "");
            }
        }) {
            //获取请求json参数字符串
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("code", text);
                return params;
            }

            //获取请求头信息
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        //添加到请求队列
        addToRequestQueue(jr);
    }

    private <T> void gsonBeanReqest(String url, BaseRequest request, Class<T> clazz, final
    CommonResponseListener<T> listener) {

        //创建请求json参数
        Gson gson = new Gson();
        final String text = gson.toJson(request);
        //开启日志
        LogUtils.i(TAG + "=url", url);
        LogUtils.i(TAG + "=request", text);

        GsonRequest<T> gr = new GsonRequest<T>(Request.Method.POST, url,
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        listener.onErrorResult(null, error.getMessage(), "");
                    }
                }, new Response.Listener<T>() {

            @Override
            public void onResponse(T bean) {
                listener.onSucceed(bean);
            }
        }, clazz) {
            //获取请求json参数字符串
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("code", text);
                return params;
            }

            //获取请求头信息
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        //添加到请求队列
        addToRequestQueue(gr);
    }

    private void stringRequest(String url, BaseRequest request, final CommonResponseListener<String> listener) {

        //创建请求json参数
        Gson gson = new Gson();
        final String text = gson.toJson(request);
        //开启日志
        LogUtils.i(TAG + "=url", url);
        LogUtils.i(TAG + "=request", text);

        //创建一个StringRequest做网络请求
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            //请求成功回调
            @Override
            public void onResponse(String response) {
                //                try {
                //                    JSONObject resp = new JSONObject(new JSONTokener(response));
                //                    int result = resp.optInt("result");
                //                    if (result == 1) {
                //                        //成功
                listener.onSucceed(response);

                //                    } else {
                //                        //失败
                //                        String msg = resp.optString("msg");
                //                        String code = resp.optString("code");
                //                        listener.onErrorResult(response, msg, code);
                //
                //                    }
                //                } catch (JSONException e) {
                //                    e.printStackTrace();
                //                    listener.onErrorResult(null, "数据错误！", "");
                //                }

            }
        }, new Response.ErrorListener() {
            //请求错误监听
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                listener.onErrorResult(null, error.getMessage(), "");
            }
        }) {
            //获取请求json参数字符串
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("code", text);
                return params;
            }

            //获取请求头信息
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        //添加到请求队列
        addToRequestQueue(sr);
    }

    /**
     * 取消所有带有tag标记的request
     * 如果传入null取消请求队列中所有请求
     * 一般建议写在，Activity / Fragment 的 onDestory 方法中
     */
    public void cancelAllRequest(Object tag) {
        mRequestQueue.cancelAll(tag);
    }
    /** 取消某个请求*/
    public void cancelRequest(Request request ) {
        request.cancel();
    }


    /**
     * 通用监听事件
     */
    public interface CommonResponseListener<T> {

        /**
         * 成功数据 result == 1的时候
         */
        void onSucceed(T response);

        /**
         * 数据错误 result ！= 1的时候
         *
         * @param response：result   ！=1 的时候为原始的JSONObject对象，异常的话就为null
         * @param errorMessage：错误消息
         * @param errorCode：错误code  异常的话就为 ” “
         */
        void onErrorResult(T response, String errorMessage, String errorCode);

    }
}
