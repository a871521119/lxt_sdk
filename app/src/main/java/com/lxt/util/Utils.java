package com.lxt.util;

import android.content.Context;

import com.lxt.app.LxtApplication;
import com.lxt.sdk.util.PhoneInfo;

public class Utils {


    /**
     * 防重复点击
     *
     * @return true 表示两次点击时间小于1500毫秒 false表示两次点击时间大于500毫秒
     */
    private static long lastClickTime = 0;

    public synchronized static boolean isFirstClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 可预约时间
     *
     * @param time
     * @return
     */
    public static String getRealTime(String time) {
        String[] timeByte = time.split(":");
        if (Integer.parseInt(timeByte[1]) == 0) {
            return timeByte[0] + ":30";
        }
        if (Integer.parseInt(timeByte[1]) == 30) {
            return (Integer.parseInt(timeByte[0])) + ":00";
        }
        if (Integer.parseInt(timeByte[1]) > 0 && Integer.parseInt(timeByte[1]) < 30) {
            return (Integer.parseInt(timeByte[0]) + 1) + ":00";
        }
        if (Integer.parseInt(timeByte[1]) > 30) {
            return (Integer.parseInt(timeByte[0]) + 1) + ":30";
        }
        return (Integer.parseInt(timeByte[0]) + 1) + ":00";
    }

    /**
     * 获取预约时间点
     *
     * @param mytime
     * @return
     */
    public static String getShrottime(String mytime) {
        String shortime = "0";
        if (mytime.equals("09:00")) {
            shortime = "1";
        } else if (mytime.equals("09:30")) {
            shortime = "2";
        } else if (mytime.equals("10:00")) {
            shortime = "3";
        } else if (mytime.equals("10:30")) {
            shortime = "4";
        } else if (mytime.equals("11:00")) {
            shortime = "5";
        } else if (mytime.equals("11:30")) {
            shortime = "6";
        } else if (mytime.equals("12:00")) {
            shortime = "7";
        } else if (mytime.equals("12:30")) {
            shortime = "8";
        } else if (mytime.equals("13:00")) {
            shortime = "9";
        } else if (mytime.equals("13:30")) {
            shortime = "10";
        } else if (mytime.equals("14:00")) {
            shortime = "11";
        } else if (mytime.equals("14:30")) {
            shortime = "12";
        } else if (mytime.equals("15:00")) {
            shortime = "13";
        } else if (mytime.equals("15:30")) {
            shortime = "14";
        } else if (mytime.equals("16:00")) {
            shortime = "15";
        } else if (mytime.equals("16:30")) {
            shortime = "16";
        } else if (mytime.equals("17:00")) {
            shortime = "17";
        } else if (mytime.equals("17:30")) {
            shortime = "18";
        } else if (mytime.equals("18:00")) {
            shortime = "19";
        } else if (mytime.equals("18:30")) {
            shortime = "20";
        } else if (mytime.equals("19:00")) {
            shortime = "21";
        } else if (mytime.equals("19:30")) {
            shortime = "22";
        } else if (mytime.equals("20:00")) {
            shortime = "23";
        } else if (mytime.equals("20:30")) {
            shortime = "24";
        } else if (mytime.equals("21:00")) {
            shortime = "25";
        } else if (mytime.equals("21:30")) {
            shortime = "26";
        } else if (mytime.equals("22:00")) {
            shortime = "27";
        } else if (mytime.equals("22:30")) {
            shortime = "28";
        }
        return shortime;
    }

    /**
     * 获取group_guid
     */
    public static String getGroupGuid() {
        return PhoneInfo.getMetaData(LxtApplication.getAppContext(), "GROUP_GUID");
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
