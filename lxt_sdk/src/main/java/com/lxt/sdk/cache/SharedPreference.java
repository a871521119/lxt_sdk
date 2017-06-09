package com.lxt.sdk.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.lxt.sdk.LXTSDK;

public class SharedPreference {

    public static void setData(String name, String value){
        SharedPreferences setShared = LXTSDK.getAppContext().getSharedPreferences("state",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setShared.edit();
        editor.putString(name, value);
        editor.commit();
    }


    public static String getData(String name){
        SharedPreferences sp = LXTSDK.getAppContext().getSharedPreferences("state",Context.MODE_PRIVATE);
        return sp.getString(name, "");
    }


}
