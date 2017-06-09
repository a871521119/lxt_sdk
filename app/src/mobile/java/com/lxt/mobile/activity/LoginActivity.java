package com.lxt.mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.lxt.R;
import com.lxt.base.BaseBeen;
import com.lxt.been.CampusChooseBeen;
import com.lxt.mobile.base.MBaseActivity;
import com.lxt.mobile.utils.SharedUtils;
import com.lxt.sdk.http.LxtHttp;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.sdk.util.JsonUtils;
import com.lxt.util.FormatUtil;
import com.lxt.util.Utils;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.List;


/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/12 9:42
 * @description : 登陆页面
 */
public class LoginActivity extends MBaseActivity {

    /**
     * 登陆输入框
     */
    EditText loginEditText;

    /**
     * 密码输入框
     */
    EditText psdEditText;

    /**
     * 登录按钮
     */
    Button login_button;

    /**
     * Toast提示文字
     */
    TextView toastText;

    /**
     * 用户名和密码
     */
    String userName, password, mSchool_guid;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_login);
    }


    @Override
    public void initView() {
        loginEditText = (EditText) findViewById(R.id.username_edittext);
        psdEditText = (EditText) findViewById(R.id.password_edittext);
        login_button = (Button) findViewById(R.id.login_button);
        toastText = (TextView) findViewById(R.id.error_show_Login_text);
    }

    @Override
    public void initListener() {
        super.initListener();
        login_button.setOnClickListener(this);
    }

    @Override
    protected void onClickView(View v) {
        super.onClickView(v);
        switch (v.getId()) {
            case R.id.login_button:
                if (check()) {
                    chooseSchool();
                }
                break;
            default:

                break;
        }
    }

    /**
     * 检测是否符合登陆要求
     *
     * @return
     */
    public boolean check() {
        if (!FormatUtil.isMobileNO(loginEditText.getText().toString().trim())) {
            showToast(getString(R.string.phoneNOErro));
            return false;
        }
        if (TextUtils.isEmpty(psdEditText.getText().toString())) {
            showToast(getString(R.string.psdErro));
            return false;
        }
        userName = loginEditText.getText().toString().trim();
        password = psdEditText.getText().toString();
        return true;
    }

    /**
     * 选择校区
     */
    public void chooseSchool() {
        showProgressDialog(getString(R.string.login_state));
        LxtHttp.getInstance().setCallBackListener(this);
        LxtHttp.getInstance().lxt_getSchoolGuid(userName, password, Utils.getGroupGuid());
    }

    /**
     * 登陆
     */
    public void login(String school_guid) {
        showProgressDialog(getString(R.string.login_state));
        mSchool_guid = school_guid;
        LxtHttp.getInstance().lxt_login(userName, password, school_guid);
    }

    @Override
    public void onSuccessed(String action, String result) {
        super.onSuccessed(action, result);
        if (action.equals(LxtParameters.Action.GET_SCHOOL_GUID)) {
            try {
                String data = JsonUtils.getValue(result, "ResultData");
                JSONObject jsonObject = new JSONObject(data);
                String msg = jsonObject.getString("message");
                List<CampusChooseBeen> list = JsonUtils.fromJsonArray(msg, CampusChooseBeen.class);
                if (list == null || list.size() == 0) {
                    //showToast("");
                    return;
                }
                if (list.size() == 1)
                    login(list.get(0).getSchool_guid());
                else {//跳转选择校区
                    Intent intent = new Intent(this, ChooseCampusActivity.class);
                    intent.putExtra("data", (Serializable) list);
                    //选择校区登录
                    intent.putExtra("name", userName);
                    intent.putExtra("password", password);
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void bindViewData(BaseBeen data) {
        super.bindViewData(data);
        SharedUtils.loginHandle(this,data,mSchool_guid);
    }


}
