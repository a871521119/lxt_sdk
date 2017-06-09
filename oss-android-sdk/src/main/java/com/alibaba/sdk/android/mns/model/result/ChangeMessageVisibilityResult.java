package com.alibaba.sdk.android.mns.model.result;

import com.alibaba.sdk.android.mns.model.MNSResult;
import com.alibaba.sdk.android.mns.model.Message;

import java.util.Date;

/**
 *   @copyright : 北京乐学通教育科技有限公司 2017
 *   @creator : bryan
 *   @create-time : 11/24/15 13:34
 *   @description :
 */
public class ChangeMessageVisibilityResult extends MNSResult {
    private String receiptHandle;
    private Date nextVisibleTime;

    public void setReceiptHandle(String receiptHandle) {
        this.receiptHandle = receiptHandle;
    }

    public String getReceiptHandle() {
        return receiptHandle;
    }

    public void setNextVisibleTime(Date nextVisibleTime) {
        this.nextVisibleTime = nextVisibleTime;
    }

    public Date getNextVisibleTime() {
        return nextVisibleTime;
    }

    public void setChangeVisibleResponse(Message message) {
        setReceiptHandle(message.getReceiptHandle());
        setNextVisibleTime(message.getNextVisibleTime());
    }
}
