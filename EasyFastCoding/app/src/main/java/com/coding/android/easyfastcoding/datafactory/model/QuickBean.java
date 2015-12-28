package com.coding.android.easyfastcoding.datafactory.model;

import easyfastcode.library.widget.adapter.ISelectable;

/**
 * Created by cy on 2015/11/30.
 */
public class QuickBean implements ISelectable {

    private String imgUrl;
    private String info;

    private boolean selected;

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

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean isSelected() {
        return  this.selected;
    }
}
