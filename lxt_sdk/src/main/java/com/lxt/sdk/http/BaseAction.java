package com.lxt.sdk.http;

import android.util.Log;
import com.lxt.sdk.listener.CallBackListener;

import java.util.Map;

/**
 * Created by LiWenJiang on 2017/5/5.
 */
 class BaseAction implements CallBackListener {
    private CallBackListener callBackListener;

    public CallBackListener getCallBackListener() {
        if (callBackListener == null)
            return this;
        return callBackListener;
    }
    public void setCallBackListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }
    @Override
    public void onSuccessed(String action, String result) {
        Log.i("OnSuccessedListener :",result);
    }

    @Override
    public void onFailed(String action, String result) {
        Log.i("onFailedListener :",result);
    }

    @Override
    public void onErrored(String action, Map<String, Object> params, String errormsg) {
        Log.i("onErroredListener :",errormsg);
    }

}
