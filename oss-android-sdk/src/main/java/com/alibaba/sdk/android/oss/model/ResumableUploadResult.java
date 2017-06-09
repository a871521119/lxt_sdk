package com.alibaba.sdk.android.oss.model;

/**
 *   @copyright : 北京乐学通教育科技有限公司 2017
 *   @creator : bryan
 *   @create-time : 11/24/15 13:34
 *   @description :
 */
public class ResumableUploadResult extends CompleteMultipartUploadResult {

    public ResumableUploadResult(CompleteMultipartUploadResult completeResult) {
        this.setBucketName(completeResult.getBucketName());
        this.setObjectKey(completeResult.getObjectKey());
        this.setETag(completeResult.getETag());
        this.setLocation(completeResult.getLocation());
        this.setRequestId(completeResult.getRequestId());
        this.setResponseHeader(completeResult.getResponseHeader());
        this.setStatusCode(completeResult.getStatusCode());
        this.setServerCallbackReturnBody(completeResult.getServerCallbackReturnBody());
    }
}
