package com.coding.android.easyfastcoding.event;

/**
 * Created by 杨强彪 on 2015/11/23.
 *
 * @描述： EventBus是一款针对Android优化的发布/订阅事件总线。主要功能是替代Intent,Handler,BroadCast在Fragment，Activity，
 * Service，线程之间传递消息优点是开销小，代码更优雅。以及将发送者和接收者解耦。
 */
public class EventDemo {

    private String mMsg;
    public EventDemo(String msg) {
        mMsg = msg;
    }
    public String getMsg(){
        return mMsg;
    }
}
