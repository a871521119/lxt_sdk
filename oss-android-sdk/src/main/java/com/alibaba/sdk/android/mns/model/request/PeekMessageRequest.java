package com.alibaba.sdk.android.mns.model.request;

import com.alibaba.sdk.android.mns.model.MNSRequest;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : bryan
 * @create-time : 2017/1/25 13:44
 * @description :
 */
public class PeekMessageRequest extends MNSRequest {
    private String queueName;

    public PeekMessageRequest(String queueName) { setQueueName(queueName); }

    public void setQueueName(String queueName) { this.queueName = queueName; }

    public String getQueueName() { return queueName; }
}
