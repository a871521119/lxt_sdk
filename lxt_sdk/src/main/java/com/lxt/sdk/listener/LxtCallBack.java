package com.lxt.sdk.listener;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/31 17:06
 * @description :
 */
public interface LxtCallBack {
    void onResponse(String result);

    void onFail(String result);
}
