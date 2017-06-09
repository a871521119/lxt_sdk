package com.lxt.mobile.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.lxt.base.AppManager;
import com.lxt.base.BaseBeen;
import com.lxt.been.UserBeen;
import com.lxt.mobile.activity.HomePageActivity;
import com.lxt.mobile.activity.LoginActivity;
import com.lxt.sdk.cache.SharedPreference;
import com.lxt.sdk.http.LxtParameters;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/16 10:37
 * @description :
 */
public class SharedUtils {
    /**
     * 登陆之后同意处理
     */
    public static void loginHandle(Context context, BaseBeen data, String school_guid) {
        if (TextUtils.equals(data.action, LxtParameters.Action.LOGIN)) {
            UserBeen user = (UserBeen) data.result;
            SharedPreference.setData(LxtParameters.Key.TOKEN, user.getToken());
            SharedPreference.setData(LxtParameters.Key.GUID, user.getGuid());
            SharedPreference.setData(LxtParameters.Key.SCHOOL_GUID, school_guid);
            SharedPreference.setData(LxtParameters.Key.LOGINSTYLE, "1");
            SharedPreference.setData(LxtParameters.Key.NAME, user.getName());
            SharedPreference.setData(LxtParameters.Key.SEX, user.getSex());
            SharedPreference.setData(LxtParameters.Key.TEL, user.getTel());
            SharedPreference.setData(LxtParameters.Key.EMAIL, user.getEmail());
            SharedPreference.setData(LxtParameters.Key.BIRTHTIME, user.getBirthtime());
            SharedPreference.setData(LxtParameters.Key.PIC, user.getPic());
            SharedPreference.setData(LxtParameters.Key.CLASSCOUNT, user.getClassCount() + "");
            AppManager.getAppManager().finishAllActivity();
            context.startActivity(new Intent(context, HomePageActivity.class));
        }
    }

    /**
     * 退出登录
     */
    public static void logoutHandle(Context context) {
        SharedPreference.setData(LxtParameters.Key.LOGINSTYLE, "0");
        Intent intent = new Intent(context, LoginActivity.class);
        AppManager.getAppManager().finishAllActivity();
        context.startActivity(intent);
    }
    /**
     * 跳转登录页
     */
    public static void jumToHomePageHandle(Context context) {
        Intent intent = new Intent(context, HomePageActivity.class);
        AppManager.getAppManager().finishAllActivity();
        context.startActivity(intent);
    }
}
