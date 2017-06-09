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
import com.lxt.sdk.util.PhoneInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/4/14 10:18
 * @description : 意见反馈
 */
public class FeelBackAcctivity extends MBaseActivity {
    EditText mEditText;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_feelback);
    }


    @Override
    public void initView() {
        mEditText = (EditText) findViewById(R.id.feedback_edittext);
        ((TitleLayout) findViewById(R.id.title)).setMode(true).setTitle("意见反馈");
    }

    @Override
    public void initListener() {
        findViewById(R.id.feedback_submitbut).setOnClickListener(this);
    }

    @Override
    protected void onClickView(View v) {
        super.onClickView(v);
        switch (v.getId()) {
            case R.id.feedback_submitbut:
                sendFeelBack(mEditText.getText().toString().trim());
                break;
            default:
                break;
        }
    }

    /**
     * 用户反馈
     */
    public void sendFeelBack(String msg) {
        if (TextUtils.isEmpty(msg)) {
            showToast(getString(R.string.fell_fellBackNotNull));
            return;
        }
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("school_guid", SharedPreference.getData(LxtParameters.Key.SCHOOL_GUID));
        objectMap.put("guid", SharedPreference.getData(LxtParameters.Key.GUID));
        objectMap.put("version", PhoneInfo.getVersion(this));
        objectMap.put("sys", "android " + android.os.Build.VERSION.RELEASE);
        objectMap.put("phone_brand", PhoneInfo.getphoneManufacturerString());
        objectMap.put("token", SharedPreference.getData(LxtParameters.Key.TOKEN));
        objectMap.put("content", msg);
        HttpPost httpPost = new HttpPost();
        httpPost.requestPost("suggest", objectMap, new CallBackListener() {
            @Override
            public void onSuccessed(String action, String result) {
                showToast(getString(R.string.fell_thanksForBack));
                mEditText.setText("");
                mEditText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);
            }

            @Override
            public void onFailed(String action, String result) {
                showToast(getString(R.string.fell_fellBackFaild));
            }

            @Override
            public void onErrored(String action, Map<String, Object> params, String errormsg) {
            }
        }, true);
    }
}
