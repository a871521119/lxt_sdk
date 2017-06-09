package com.alibaba.sdk.android.mns.model.request;

import com.alibaba.sdk.android.mns.model.MNSRequest;
import com.alibaba.sdk.android.mns.model.QueueMeta;

/**
 *   @copyright : 北京乐学通教育科技有限公司 2017
 *   @creator : bryan
 *   @create-time : 11/24/15 13:34
 *   @description :
 */
public class SetQueueAttributesRequest extends MNSRequest {
    // 队列名称
    private String queueName;
    private QueueMeta queueMeta;

    public SetQueueAttributesRequest(String queueName){
        setQueueName(queueName);
    }

    private void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueName(){
        return queueName;
    }

    public void setQueueMeta(QueueMeta queueMeta) {this.queueMeta = queueMeta;}

    public QueueMeta getQueueMeta() { return queueMeta;}
}
