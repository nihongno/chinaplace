package com.yiluxiangbei.chinaplace.util;

/**
 * Created by yiluxiangbei on 2016/4/14.
 */
public interface HttpCallbackListener {

    void onFinish(String response);
    void onError(Exception e);
}
