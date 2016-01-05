package com.coding.android.easyfastcoding.view.model;

import java.util.List;

import easyfastcode.library.widget.adapter.ISelectable;

/**
 * Created by cy on 2016/1/5.
 */
public class DemoItemModel implements ISelectable {

    private boolean isSelect;
    public String name;
    public String info;
    public int type;
    public int depth;
    public String content;

    @Override
    public void setSelected(boolean selected) {
        isSelect = selected;
    }

    @Override
    public boolean isSelected() {
        return isSelect;
    }
}
