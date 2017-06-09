package com.lxt.mobile.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.lxt.app.LxtApplication;
import com.lxt.sdk.util.PhoneInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/17 10:28
 * @description :
 */
public class LxtUtils {
    /**
     * String 转换成 date
     */
    public static Date stringConversionDate(String dateString) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return date;
    }

    /**
     * 获取开课时间与当前时间的时间差
     *
     * @param TheCurrent 当前时间
     * @param classes    开课时间
     * @return 时间差
     */
    public static long timeDifference(String TheCurrent, String classes) {
        /**
         * String转换成date
         */
        Date mTheCurrent = LxtUtils.stringConversionDate(TheCurrent);
        Date mClasses = LxtUtils.stringConversionDate(classes);
        /**
         * 转换成毫秒
         */
        long mTheCurrentms = mTheCurrent.getTime();
        long mClassesms = mClasses.getTime();
        //相差多少毫秒
        return mClassesms - mTheCurrentms;
    }

    /**
     * @param mss 要转换的毫秒数
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     * @author fy.zhang
     */
    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        if (days <= 0) {
            return hours + ":" + minutes + ":" + seconds;
        }
        return days + ":" + hours + ":" + minutes + ":"
                + seconds + ":";
    }

    /**
     * @param begin 时间段的开始
     * @param end   时间段的结束
     * @return 输入的两个Date类型数据之间的时间间格用* days * hours * minutes * seconds的格式展示
     * @author fy.zhang
     */
    public static String formatDuring(Date begin, Date end) {
        return formatDuring(end.getTime() - begin.getTime());
    }
    /**
     * 检查更新
     */
    public static void checkForUpData(final Context context, final String url, final boolean canCancel) {
        final AlertDialog.Builder builer = new AlertDialog.Builder(context);
        builer.setCancelable(false);
        builer.setTitle("版本升级");
        builer.setCancelable(canCancel);
        builer.setMessage("检测到最新版本，请及时更新！");
        //当点确定按钮时从服务器上下载 新的apk 然后安装
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                UpdateAppManager.downloadApk(context, url, "下载中...", PhoneInfo.getMetaData(LxtApplication.getAppContext(), "APPNAME"));
                if (!canCancel) {
                    System.exit(0);
                }
            }
        });
        if (canCancel) {
            builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        }
        AlertDialog dialog = builer.create();
        dialog.show();
    }
}
