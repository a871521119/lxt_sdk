package com.lxt.sdk.lib.xutils.http.app;


import com.lxt.sdk.lib.xutils.http.RequestParams;
import com.lxt.sdk.lib.xutils.http.request.UriRequest;

/**
 * 请求重定向控制接口
 */
public interface RedirectHandler {

    /**
     * 根据请求信息返回自定义重定向的请求参数
     *
     * @param request
     * @return 返回不为null时进行重定向
     */
    RequestParams getRedirectParams(UriRequest request) throws Throwable;
}
