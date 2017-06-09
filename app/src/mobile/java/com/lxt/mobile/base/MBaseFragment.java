package com.lxt.mobile.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lxt.base.AppManager;
import com.lxt.base.BaseBeen;
import com.lxt.base.BaseFragment;
import com.lxt.mobile.activity.LoginActivity;
import com.lxt.sdk.cache.SharedPreference;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.sdk.listener.CallBackListener;
import com.lxt.util.ParseJsonUtil;
import com.lxt.util.ToastUitl;
import com.lxt.widget.LoadingDialog;

import java.util.Map;


/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/3/29 11:19
 * @description :
 */
public abstract class MBaseFragment extends BaseFragment implements View.OnClickListener, CallBackListener {
    /**
     * 是否正在刷新
     */
    private boolean isRefreshing = false;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = getSuccessView();
        initView();
        initListener();
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        load();
    }

    /**
     * 设置布局文件
     */
    public abstract View setContentLayout(LayoutInflater inflater);

    /**
     * 实例化布局文件/组件
     */
    public abstract void initView();

    /**
     * 加载数据
     */
    public abstract void load();

    /**
     * 初始化监听
     */
    protected void initListener() {
    }

    /**
     * 在实例化布局之后处理的逻辑
     */
    public void bindViewData(BaseBeen data) {
    }

    /**
     * 成功页面
     */
    public View getSuccessView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        return setContentLayout(inflater);
    }

    /**
     * 短时间显示Toast
     *
     * @param info 显示的内容
     */
    public void showToast(String info) {
        ToastUitl.showShort(info);
    }

    @Override
    public void onSuccessed(String action, String result) {
        LoadingDialog.cancelDialogForLoading();
        isRefreshing = false;
        bindViewData(ParseJsonUtil.parse(action, result));
    }

    /**
     * 请求失败或发生错误调用此方法
     *
     * @param result 返回错误码
     * @param action 主要是为了区分接口，防止Activity中多个网络请求无法区分
     */
    @Override
    public void onFailed(String action, String result) {
        LoadingDialog.cancelDialogForLoading();
        isRefreshing = false;
        BaseBeen base = ParseJsonUtil.parse(action,result);
        ToastUitl.showShort((String)base.result);
        if (base.ServerNo.equals("SN005") || base.ServerNo.equals("SN008") || base.ServerNo.equals("SN009")) {
            SharedPreference.setData(LxtParameters.Key.LOGINSTYLE,"0");
            AppManager.getAppManager().finishAllActivity();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }else {
        }
    }

    /**
     * 请求失败
     */
    @Override
    public void onErrored(String action, Map<String, Object> params, String errormsg) {
        LoadingDialog.cancelDialogForLoading();
        isRefreshing = false;
        showToast(errormsg);
    }
}
