package com.lxt.tv.activity;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lxt.R;
import com.lxt.been.UserBeen;
import com.lxt.sdk.cache.SharedPreference;
import com.lxt.sdk.http.LxtHttp;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.base.BaseBeen;
import com.lxt.tv.base.MBaseActivity;
import com.lxt.util.FormatUtil;
import com.lxt.util.ToastUitl;




/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/3/28 15:28
 * @description :
 */
public class LoginActivity extends MBaseActivity implements View.OnClickListener {
    /**
     * 用户名
     */
    private EditText LoginUserNameSetEditText;
    /**
     * 密码
     */
    private EditText LoginUserPasswdSetEditText;
    /**
     * 登陆
     */
    private Button loginSubmitButton;
    /**
     * 接收用户姓名,密码
     */
    private String userName, passWord;
    /**
     * 学校的guid
     */
    String mSchool_guid = "19c603b8352d11e7b3d500163e106fb7";//c69bcd14135b11e785b800163e106fb7  41a0981ca1c611e6994800163e032fb9
    //登录模式 true 直接登录  ，false 逻辑判断登录
    private static boolean LOGIN_MODEL = false;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initView() {
        LoginUserNameSetEditText = (EditText) findViewById(R.id.login_username_set_edittext);
        LoginUserPasswdSetEditText = (EditText) findViewById(R.id.login_userpasswd_set_edittext);
        loginSubmitButton = (Button) findViewById(R.id.login_submit_button);
    }

    @Override
    public void initListener() {
        super.initListener();
        LxtHttp.getInstance().setCallBackListener(this);
        loginSubmitButton.setOnClickListener(this);
    }

    @Override
    protected void onClickView(View v) {
        super.onClickView(v);
        if (v.getId() == R.id.login_submit_button) {
            if (checkIsOk()) {
               // campusChooseHttp();
         LxtHttp.getInstance().lxt_login(userName,passWord,mSchool_guid);
            }
        }
    }

    /**
     * 检测是否符合登陆要求
     *
     * @return
     */
    private boolean checkIsOk() {
        if (TextUtils.isEmpty(LoginUserNameSetEditText.getText().toString().trim())) {
            ToastUitl.showShort(getString(R.string.phoneNumIsNotNull));
            return false;
        }
        if (TextUtils.isEmpty(LoginUserPasswdSetEditText.getText().toString().trim())) {
            ToastUitl.showShort(getString(R.string.pswIsNotNull));
            return false;
        }
        if (!FormatUtil.isMobileNO(LoginUserNameSetEditText.getText().toString().trim())) {
            ToastUitl.showShort(getString(R.string.phoneNumIsErro));
            return false;
        }
        userName = LoginUserNameSetEditText.getText().toString().trim();
        passWord = LoginUserPasswdSetEditText.getText().toString().trim();
        return true;
    }

    @Override
    protected void bindViewData(BaseBeen data) {
        if (TextUtils.equals(LxtParameters.Action.LOGIN,data.action)){
            UserBeen user = (UserBeen) data.result;
            SharedPreference.setData(LxtParameters.Key.TOKEN,user.getToken());
            SharedPreference.setData(LxtParameters.Key.GUID,user.getGuid());
            SharedPreference.setData(LxtParameters.Key.SCHOOL_GUID,mSchool_guid);
            SharedPreference.setData(LxtParameters.Key.LOGINSTYLE,"true");
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

    }

    /**
     * 登录 网络请求
     */
    private void campusChooseHttp() {
        showProgressDialog();
//        Map<String, Object> map = new HashMap<>();
//        map.put(Parameters.Key.PHONE, userName);
//        map.put(Parameters.Key.PASSWORD, passWord);
//        map.put(Parameters.Key.GROUP_GUID, Parameters.GROUP_GUID);
//        getHttpResult(map, Parameters.Action.GET_SCHOOL_GUID);
    }

    /**
     * 登录 携带校区
     */
    private void setLoginHttp() {
//        Map<String, Object> map = new HashMap<>();
//        map.put(Parameters.Key.PHONE, userName);
//        map.put(Parameters.Key.PASSWORD, passWord);
//        map.put(Parameters.Key.REGISTRATION_ID, Constants.AURORA_PUSH_ID);
//        map.put(Parameters.Key.SCHOOL_GUID, mSchool_guid);
//        getHttpResult(map, Parameters.Action.LOGIN);
    }

//    @Override
//    public void onSuccessCallback(String str, String action) {
//        super.onSuccessCallback(str, action);
//        if (action.equals(Parameters.Action.LOGIN)) {
//            UserBeen mLoginVo = (UserBeen) JsonUtils.fromJson(str, UserBeen.class);
//            SharedPreference.setData(mLoginVo.getToken(), Parameters.Key.SHARED_TOKEN, mContext);
//            SharedPreference.setData(mLoginVo.getGuid(), Parameters.Key.GUID, mContext);
//            SharedPreference.setData(mSchool_guid , Parameters.Key.SCHOOL_GUID, mContext);
//            SharedPreference.setData(str,Parameters.Key.SHARED_USER_INFO,mContext);
//            SharedPreference.setData("true", Parameters.Key.SHARED_LOGINSTYLE, this);
//            startActivity(new Intent(this, HomeActivity.class));
//            finish();
//        } else {
//            try {
//                JSONObject jsonObject = new JSONObject(str);
//                List<CampusChooseBeen> campusChooseBeens = JsonUtils.fromJsonArray(jsonObject.optString("message"), CampusChooseBeen.class);
//                if (LOGIN_MODEL) {//直接登录
//                    mSchool_guid = campusChooseBeens.get(0).getSchool_guid();
//                    setLoginHttp();
//                } else {//逻辑判断登录
//                    if (campusChooseBeens.size() <= 1) {
//                        //直接登录
//                        mSchool_guid = campusChooseBeens.get(0).getSchool_guid();
//                        setLoginHttp();
//                    } else {
//                        //选择校区登录
//                        Bundle bundle = new Bundle();
//                        bundle.putString(Parameters.Key.PHONE, userName);
//                        bundle.putString(Parameters.Key.PASSWORD, passWord);
//                        bundle.putString("campusData", jsonObject.optString("message"));
//                        startActivity(ChooseScoolActivity.class, bundle);
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
//        }
//    }

}
