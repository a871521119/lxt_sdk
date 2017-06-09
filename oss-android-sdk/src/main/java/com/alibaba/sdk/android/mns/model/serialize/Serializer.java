package com.alibaba.sdk.android.mns.model.serialize;

/**
 *   @copyright : 北京乐学通教育科技有限公司 2017
 *   @creator : bryan
 *   @create-time : 11/24/15 13:34
 *   @description :
 */
public interface Serializer<T> {
    String serialize(T obj, String encoding) throws Exception;
}