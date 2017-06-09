package com.lxt.mobile.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lxt.base.AppManager;
import com.lxt.base.BaseActivity;
import com.lxt.base.BaseBeen;
import com.lxt.mobile.activity.LoginActivity;
import com.lxt.sdk.cache.SharedPreference;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.sdk.listener.CallBackListener;
import com.lxt.util.ParseJsonUtil;
import com.lxt.util.ToastUitl;

import java.util.Map;


/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/3/28 14:41
 * @description :
 */
public abstract class MBaseActivity extends BaseActivity implements CallBackListener {

    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentLayout();
        initView();
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
    }

    public void initData() {
    }

    public void showToast(String msg) {
        ToastUitl.showShort(msg);
    }


    /**
     * 请求成功时调用此方法
     *
     * @param result 请求结果ResultData下的内容
     * @param action
     */
    @Override
    public void onSuccessed(String action, String result) {
        dismissProgressDialog();
        bindViewData(ParseJsonUtil.parse(action, result));
    }

    /**
     * View数据绑定
     *
     * @param data
     */
    protected void bindViewData(BaseBeen data) {
    }

    ;


    /**
     * 请求失败或发生错误调用此方法
     *
     * @param result 返回错误码
     * @param action 主要是为了区分接口，防止Activity中多个网络请求无法区分
     */

    @Override
    public void onFailed(String action, String result) {
        dismissProgressDialog();
        BaseBeen base = ParseJsonUtil.parse(action,result);
        showToast((String)base.result);
        if (base.ServerNo.equals("SN005") || base.ServerNo.equals("SN008") || base.ServerNo.equals("SN009")) {
            ToastUitl.showShort("登陆过期，请重新登录");
            SharedPreference.setData(LxtParameters.Key.LOGINSTYLE, "0");
            AppManager.getAppManager().finishAllActivity();
            startActivity(new Intent(mContext, LoginActivity.class));
        }
    }

    /**
     * 获取学生信息
     */
    public String getValue(String key) {
        return SharedPreference.getData(key);
    }

    /**
     * 请求失败
     */
    @Override
    public void onErrored(String action, Map<String, Object> params, String errormsg) {
        dismissProgressDialog();
        showToast(errormsg);
    }
}
