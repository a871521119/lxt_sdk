package com.alibaba.sdk.android.oss.model;

/**
 *   @copyright : 北京乐学通教育科技有限公司 2017
 *   @creator : bryan
 *   @create-time : 11/24/15 13:34
 *   @description :
 */
public class CreateBucketResult extends OSSResult {
    // bucket所在数据中心
    private String bucketLocation;

    /**
     * 设置bucket所在数据中心
     * @param location
     */
    public void setBucketLocation(String location) {
        this.bucketLocation = location;
    }

    /**
     * 返回bucket所在数据中心
     * @return
     */
    public String getBucketLocation() {
        return bucketLocation;
    }
}
