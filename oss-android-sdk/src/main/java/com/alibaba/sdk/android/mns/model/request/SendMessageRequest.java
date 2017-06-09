package com.alibaba.sdk.android.mns.model.request;

import com.alibaba.sdk.android.mns.model.MNSRequest;
import com.alibaba.sdk.android.mns.model.Message;

/**
 *   @copyright : 北京乐学通教育科技有限公司 2017
 *   @creator : bryan
 *   @create-time : 11/24/15 13:34
 *   @description :
 */
public class SendMessageRequest extends MNSRequest {
    private String queueName;
    private Message message;

    public SendMessageRequest(String queueName) {
        setQueueName(queueName);
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
