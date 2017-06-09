package com.alibaba.sdk.android.oss.network;

import okhttp3.Call;

/**
 *   @copyright : 北京乐学通教育科技有限公司 2017
 *   @creator : bryan
 *   @create-time : 11/24/15 13:34
 *   @description :
 */
public class CancellationHandler {

    private volatile boolean isCancelled;

    private volatile Call call;

    public void cancel() {
        if (call != null) {
            call.cancel();
        }
        isCancelled = true;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCall(Call call) {
        this.call = call;
    }
}
