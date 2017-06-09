package com.lxt.sdk.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.UUID;

public class PhoneInfo {

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInputMode(Context context, View windowToken) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(windowToken.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 获得channel id
     *
     * @param context <meta-data android:name="SZICITY_CHANNEL" android:value=
     *                "CHANNEL108"/>
     * @return
     */
    public static String getMetaData(Context context, String key) {
        String CHANNELID = "";
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            CHANNELID = ai.metaData.getString(key);
        } catch (Exception e) {
        }
        return CHANNELID;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "获取错误";
        }
    }

    /**
     * 获取设备微议识别码
     */
    public static String getUniqueID(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.
                getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());

        return deviceUuid.toString();
    }

    /**
     * 获取当前的手机号
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String number = tManager.getLine1Number();
        if (number != null && number.startsWith("+")) {
            String newNumber = number.substring(3, number.length());
            return newNumber;
        }
        return number;
    }


    /**
     * 获取当前设备号
     *
     * @param context
     * @return
     */
    public static String getPhoneIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public static int[] getScreenInfo(Context mContext) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = mContext.getResources().getDisplayMetrics();
        int info[] = {dm.widthPixels, dm.heightPixels};
        return info;
    }

    /**
     * 获取手机厂商
     *
     * @return
     */
    public static String phoneManufacturer() {
        return android.os.Build.MANUFACTURER;
    }

    public static String getphoneManufacturerString() {
        String phoneManufacturer = phoneManufacturer();
        if (!TextUtils.isEmpty(phoneManufacturer)) {
            if (phoneManufacturer.equals("smartisan")) {
                return "chuizi";
            } else if (phoneManufacturer.equals("HTC")) {
                return "htc";
            } else if (phoneManufacturer.equals("HUAWEI")) {
                return "huawei";
            } else if (phoneManufacturer.equals("Meizu")) {
                return "meizu";
            } else if (phoneManufacturer.equals("OPPO")) {
                return "oppo";
            } else if (phoneManufacturer.equals("samsung")) {
                return "sanxing";
            } else if (phoneManufacturer.equals("vivo")) {
                return "vivo";
            } else if (phoneManufacturer.equals("Xiaomi")) {
                return "xiaomi";
            } else if (phoneManufacturer.equals("OnePlus")) {
                return "yijia";
            }
        }
        return phoneManufacturer;
    }

    /**
     * 获取状态栏高度
     */
    public int getStatusBarHeight(Context context) {
        int statusBarHeight = -1;
//获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            return statusBarHeight;
        }
        return 0;
    }
}
