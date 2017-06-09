package com.lxt.tv.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.lxt.base.AppManager;
import com.lxt.base.BaseActivity;
import com.lxt.base.BaseBeen;
import com.lxt.sdk.cache.SharedPreference;
import com.lxt.sdk.http.LxtHttp;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.sdk.listener.CallBackListener;
import com.lxt.tv.activity.LoginActivity;
import com.lxt.util.ParseJsonUtil;
import com.lxt.util.ToastUitl;

import java.util.Map;



/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/3/28 14:41
 * @description :
 */
public abstract class MBaseActivity extends BaseActivity implements CallBackListener{

    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mContext = this;
        setContentLayout();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initListener();
        initData();
    }

    /**
     * 设置布局文件
     */
    public abstract void setContentLayout();

    /**
     * 实例化布局文件/组件
     */
    public abstract void initView();

    public void initListener() {
        LxtHttp.getInstance().setCallBackListener(this);
    }

    public void initData() {
    }

    public void showToast(String msg) {
        ToastUitl.showShort(msg);
    }


    /**
     * 请求成功时调用此方法
     * @param result    请求结果ResultData下的内容
     * @param action
     */
    @Override
    public void onSuccessed(String action, String result) {
        dismissProgressDialog();
        bindViewData(ParseJsonUtil.parse(action,result));
    }

    /**
     * View数据绑定
     * @param data
     */
    protected  void bindViewData(BaseBeen data){};



    /**
     * @param result  返回错误码
     * @param action 主要是为了区分接口，防止Activity中多个网络请求无法区分
     */

    @Override
    public void onFailed(String action, String result) {
        dismissProgressDialog();
        BaseBeen base = ParseJsonUtil.parse(action,result);
        ToastUitl.showLong((String) base.result);
        if (base.ServerNo.equals("SN005") || base.ServerNo.equals("SN008") || base.ServerNo.equals("SN009")) {
            ToastUitl.showShort("登陆过期，请重新登录");
            SharedPreference.setData(LxtParameters.Key.LOGINSTYLE,"exitStyle");
            AppManager.getAppManager().finishAllActivity();
            startActivity(new Intent(mContext, LoginActivity.class));
        }
    }


    /**
     * 请求失败
     */
    @Override
    public void onErrored(String action, Map<String, Object> params, String errormsg) {
        dismissProgressDialog();
        ToastUitl.showLong(errormsg);
    }
}
