package com.lxt.mobile.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.lxt.R;
import com.lxt.mobile.base.MBaseActivity;
import com.lxt.mobile.widget.TitleLayout;
import com.lxt.sdk.cache.SharedPreference;
import com.lxt.sdk.http.HttpPost;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.sdk.listener.CallBackListener;
import com.lxt.sdk.util.JsonUtils;

import java.util.HashMap;
import java.util.Map;

public class UpdataPasswordActivity extends MBaseActivity {
    /**
     * 新旧密码
     */
    private EditText oldpwd, newpwd;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_updata_password);
    }

    @Override
    public void initView() {
        oldpwd = (EditText) findViewById(R.id.old_password);
        newpwd = (EditText) findViewById(R.id.new_password);
        ((TitleLayout) findViewById(R.id.title)).setMode(true).setTitle("修改密码");
    }

    @Override
    public void initListener() {
        super.initListener();
        findViewById(R.id.updata_submitbut).setOnClickListener(this);
    }

    public boolean check() {
        if (TextUtils.isEmpty(oldpwd.getText().toString())) {
            showToast(getString(R.string.the_old_passwd_notNone));
            return false;
        }

        if (TextUtils.isEmpty(newpwd.getText().toString())) {
            showToast(getString(R.string.new_passwd_notNone));
            return false;
        }
        if (TextUtils.equals(oldpwd.getText().toString(), newpwd.getText().toString())) {
            showToast(getString(R.string.new_old_passwordNotEaque));
            return false;
        }
        return true;
    }

    public void updataPassword() {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("school_guid", SharedPreference.getData(LxtParameters.Key.SCHOOL_GUID));
        objectMap.put("student_guid", SharedPreference.getData(LxtParameters.Key.GUID));
        objectMap.put("password", oldpwd.getText().toString());
        objectMap.put("newpassword", newpwd.getText().toString());
        objectMap.put("token", SharedPreference.getData(LxtParameters.Key.TOKEN));
        HttpPost httpPost = new HttpPost();
        httpPost.requestPost("updateMePassword", objectMap, new CallBackListener() {
            @Override
            public void onSuccessed(String action, String result) {
                showToast(getString(R.string.passwordUpdataSuc));
                finish();
            }

            @Override
            public void onFailed(String action, String result) {
                String data = JsonUtils.getValue(result, "ResultData");
                showToast(data);
            }

            @Override
            public void onErrored(String action, Map<String, Object> params, String errormsg) {
            }
        }, true);
    }


    @Override
    protected void onClickView(View v) {
        super.onClickView(v);
        switch (v.getId()) {
            case R.id.updata_submitbut:
                if (check()) {
                    updataPassword();
                }
                break;
            default:

                break;
        }
    }
}
