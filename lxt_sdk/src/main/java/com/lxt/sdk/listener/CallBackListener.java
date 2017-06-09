package com.lxt.sdk.listener;

import java.util.Map;

/**
 * 网络数据回调监听
 * Created by LiWenJiang on 2017/5/5.
 */

public interface CallBackListener {
    /**
     * 请求数据成功code=200
     * @param action 请求接口Action
     * @param result 返回的数据
     */
    void onSuccessed(String action,String result);

    /**
     * 请求数据失败
     * @param action
     * @param result
     */
    void onFailed(String action,String result);

    /**
     * 请求数据错误
     * @param action 请求接口Action
     * @param params 请求接口参数集合
     * @param errormsg 返回的错误信息
     */
    void onErrored(String action, Map<String,Object> params, String errormsg);

}
