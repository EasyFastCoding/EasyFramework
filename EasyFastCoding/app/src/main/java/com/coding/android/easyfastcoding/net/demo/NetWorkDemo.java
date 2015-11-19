package com.coding.android.easyfastcoding.net.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.toolbox.NetworkImageView;
import com.coding.android.easyfastcoding.R;
import com.coding.android.easyfastcoding.common.configuration.CommonConfig;
import com.coding.android.easyfastcoding.common.utils.LogUtils;
import com.coding.android.easyfastcoding.common.utils.UIUtils;
import com.coding.android.easyfastcoding.net.VolleyHelper;
import com.coding.android.easyfastcoding.net.bean.DemoBean;
import com.coding.android.easyfastcoding.net.request.DemoRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 杨强彪 on 2015/11/19.
 *
 * @描述：
 */
public class NetWorkDemo extends Activity implements View.OnClickListener {

    @Bind(R.id.btn1)
    Button btn1;
    @Bind(R.id.btn2)
    Button btn2;
    @Bind(R.id.btn3)
    Button btn3;
    @Bind(R.id.btn4)
    Button btn4;
    @Bind(R.id.btn5)
    Button btn5;
    @Bind(R.id.btn6)
    Button btn6;
    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.niv)
    NetworkImageView niv;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initEvent();
    }

    private void initEvent() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn1:    //  TODO  StringRequest

                String stringUrl = CommonConfig.DEMO_STRING_REQUEST_URL;
                DemoRequest stringRequest = new DemoRequest();
                VolleyHelper.getInstance(UIUtils.getContext()).stringRequest_net(stringUrl, stringRequest, new
                        VolleyHelper.CommonResponseListener<String>() {

                            @Override
                            public void onSucceed(String response) {
                                LogUtils.i("StringResponse = " + response);
                            }

                            @Override
                            public void onErrorResult(String response, String errorMessage, String errorCode) {

                                LogUtils.i("StringResponse_errorMessage = " + errorMessage);
                            }
                        });
                break;
            case R.id.btn2:    //  TODO  JsonObjectRequest

                String JORurl = CommonConfig.DEMO_STRING_REQUEST_URL;
                DemoRequest JORrequest = new DemoRequest();
                VolleyHelper.getInstance(UIUtils.getContext()).jsonObjectRequest_net(JORurl, JORrequest, new
                        VolleyHelper.CommonResponseListener<JSONObject>() {


                            @Override
                            public void onSucceed(JSONObject response) {

                            }

                            @Override
                            public void onErrorResult(JSONObject response, String errorMessage, String errorCode) {

                            }
                        });
                break;
            case R.id.btn3:    //  TODO  JsonArrayRequest

                String JARurl = CommonConfig.DEMO_STRING_REQUEST_URL;
                DemoRequest JARrequest = new DemoRequest();
                VolleyHelper.getInstance(UIUtils.getContext()).jsonArrayRequest_net(JARurl, JARrequest, new
                        VolleyHelper.CommonResponseListener<JSONArray>() {


                            @Override
                            public void onSucceed(JSONArray response) {

                            }

                            @Override
                            public void onErrorResult(JSONArray response, String errorMessage, String errorCode) {

                            }
                        });
                break;
            case R.id.btn4:    //  待添加

                break;
            case R.id.btn5:    //  待添加

                break;
            case R.id.btn6:    //  TODO  自定义请求GsonRequest
                String GRurl = CommonConfig.DEMO_STRING_REQUEST_URL;
                DemoRequest GRrequest = new DemoRequest();
                VolleyHelper.getInstance(UIUtils.getContext()).gsonBeanReqest_net(GRurl, GRrequest, DemoBean.class,
                        new VolleyHelper.CommonResponseListener<DemoBean>() {
                            @Override
                            public void onSucceed(DemoBean response) {

                            }

                            @Override
                            public void onErrorResult(DemoBean response, String errorMessage, String errorCode) {

                            }
                        });
                break;

            default:
                break;
        }
    }
}
