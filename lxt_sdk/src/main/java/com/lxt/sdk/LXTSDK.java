package com.lxt.sdk;

import android.app.Application;
import android.content.Context;

import com.lxt.sdk.lib.xutils.lxt;

/**
 * Created by LiWenJiang on 2017/5/5.
 */

public class LXTSDK {

    public  static Application app;

    private static boolean isRepalceAction;//是否替换Action



    public static LXTSDK init(Application context){
        LXTSDK.app = context;
        lxt.Ext.init(context);
        lxt.Ext.setDebug(true);
        isRepalceAction = false;
        return new LXTSDK();
    }

    public void setDebug(boolean debug){
        lxt.Ext.setDebug(debug);
    }




    public static Context getAppContext(){
        return app.getApplicationContext();
    }


    public static boolean isRepalceAction() {
        return isRepalceAction;
    }
}
