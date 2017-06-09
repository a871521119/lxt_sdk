package com.lxt.sdk.util;

import android.os.Environment;

/**
 * author 高明宇
 * date 2016/7/16 9:02
 * function 公共变量
 */
public class LxtConfig {
    public static final boolean LOG_DEBUG = true;
    /**
     * 获取服务器返回的时间
     */
    public static Long SERVERTIME = 0L;
    /**
     * 当前开机时间
     */
    public static Long KAIJITIME = 0L;
    /**
     * SD卡根目录
     */
    public static final String SDROOTPath = Environment.getExternalStorageDirectory().getPath();
    /**
     * 外部存储设备的根路径下面的uhut文件夹
     */
    public static final String SDPath = SDROOTPath + "/weiyuyan/";
    /**
     * glide缓存路径设置
     */
    public static final String PICCACHE = SDPath + "pictureCache/";

    //极光推送ID
    public static String AURORA_PUSH_ID = "2";

}
