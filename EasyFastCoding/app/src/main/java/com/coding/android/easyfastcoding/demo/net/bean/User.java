package com.coding.android.easyfastcoding.demo.net.bean;

/**
 * Created by 杨强彪 on 2016/1/4.
 *
 * @描述：
 */
public  class User {
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId ;
    public User(String userId) {
        this.userId = userId;
    }
}
