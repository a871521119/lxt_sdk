package com.lxt.tv.activity;


import com.lxt.R;
import com.lxt.sdk.cache.SharedPreference;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.tv.base.MBaseActivity;

/**
 * @copyright : 北京乐学通教育科技有限公司 2016
 * @creator : 高明宇
 * @create-time : 2016/12/23 16:19
 * @description :
 */
public class BootAnimationActivity extends MBaseActivity {
    @Override
    public void setContentLayout() {
        setContentView(R.layout.boot_animation_activity);
    }

    @Override
    public void initView() {
        String loginStyle = SharedPreference.getData(LxtParameters.Key.LOGINSTYLE);
        if (loginStyle.equals("true")) {
            startActivity( HomeActivity.class);
        } else {
            startActivity(LoginActivity.class);
        }
        finish();
    }


}
