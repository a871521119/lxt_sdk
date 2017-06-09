package com.alibaba.sdk.android.oss.internal;

import java.io.IOException;

import okhttp3.Response;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : bryan
 * @create-time : 2017/1/25 13:44
 * @description :
 */
public interface ResponseParser<T> {

    T parse(Response response) throws IOException;
}
