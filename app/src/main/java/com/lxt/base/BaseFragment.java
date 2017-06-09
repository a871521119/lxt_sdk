package com.lxt.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.lxt.util.Utils;
import com.lxt.widget.LoadingDialog;


/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/3/28 13:43
 * @description :
 */
public class BaseFragment extends Fragment implements View.OnClickListener {

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (Utils.isFirstClick()) {
            return;
        }
        onClickView(v);
    }

    /**
     * 防误触的点击
     *
     * @param v
     */
    public void onClickView(View v) {
    }

    /**
     * 显示正在加载的进度条
     */
    public void showProgressDialog(String... msg) {
        if (msg != null && msg.length != 0) {
            LoadingDialog.showDialogForLoading(getActivity(), msg[0], true);
            return;
        }
        LoadingDialog.showDialogForLoading(getActivity());
    }

    /**
     * 隐藏正在加载的进度条
     */
    public void dismissProgressDialog() {
        LoadingDialog.cancelDialogForLoading();
    }
}
