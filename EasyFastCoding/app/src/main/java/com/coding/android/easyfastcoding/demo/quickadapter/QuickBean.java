package com.coding.android.easyfastcoding.demo.quickadapter;

/**
 * Created by cy on 2015/11/30.
 */
public class QuickBean {

    private String imgUrl;
    private String info;

    public QuickBean(String imgUrl, String info) {
        this.imgUrl = imgUrl;
        this.info = info;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
