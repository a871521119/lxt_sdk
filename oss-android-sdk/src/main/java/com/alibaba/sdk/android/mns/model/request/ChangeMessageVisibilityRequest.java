package com.alibaba.sdk.android.mns.model.request;

import com.alibaba.sdk.android.mns.model.MNSRequest;

/**
 *   @copyright : 北京乐学通教育科技有限公司 2017
 *   @creator : bryan
 *   @create-time : 11/24/15 13:34
 *   @description :
 */
public class ChangeMessageVisibilityRequest extends MNSRequest {
    private String queueName;
    private String receiptHandle;
    private Integer visibleTime;

    public ChangeMessageVisibilityRequest(String queueName, String receiptHandle, Integer visibleTime) {
        setQueueName(queueName);
        setReceiptHandle(receiptHandle);
        setVisibleTime(visibleTime);
    }

    public void setQueueName(String queueName) { this.queueName = queueName; }

    public String getQueueName() { return queueName; }

    public void setReceiptHandle(String receiptHandle) { this.receiptHandle = receiptHandle; }

    public String getReceiptHandle() { return receiptHandle; }

    public void setVisibleTime(Integer visibleTime) { this.visibleTime = visibleTime; }

    public Integer getVisibleTime() { return visibleTime; }
}
