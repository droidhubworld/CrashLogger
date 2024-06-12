package com.droidhubworld.crashlogger.utils;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import org.json.JSONObject;

import java.util.TimeZone;
import java.util.UUID;

public class AppUtils {
    public static String getCurrentLauncherApp(Context context) {
        String str = "";
        PackageManager localPackageManager = context.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        try {
            ResolveInfo resolveInfo = localPackageManager.resolveActivity(intent,
                    PackageManager.MATCH_DEFAULT_ONLY);
            if (resolveInfo != null && resolveInfo.activityInfo != null) {
                str = resolveInfo.activityInfo.packageName;
            }
        } catch (Exception e) {
            Logger.e("AppUtils", "Exception : " + e.getMessage());
        }
        return str;
    }

    public static String getUserIdentity(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS) ==
                PackageManager.PERMISSION_GRANTED) {
            AccountManager manager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
            Account[] list = manager.getAccounts();
            String emailId = null;
            for (Account account : list) {
                if (account.type.equalsIgnoreCase("com.google")) {
                    emailId = account.name;
                    break;
                }
            }
            if (emailId != null) {
                return emailId;
            }
        }
        return "";
    }

    public static JSONObject getDeviceDetails(Context context) {
        JSONObject deviceInfo = new JSONObject();
        try {
            deviceInfo.put("DEVICE.ID", getDeviceId(context));
//            deviceInfo.put("USER.ID", getUserIdentity(context));
            deviceInfo.put("APP.VERSION", getAppVersion(context));
//            deviceInfo.put("LAUNCHER.APP", getCurrentLauncherApp(context));
            deviceInfo.put("TIMEZONE", timeZone());
//            deviceInfo.put("VERSION.RELEASE", Build.VERSION.RELEASE);
//            deviceInfo.put("VERSION.INCREMENTAL", Build.VERSION.INCREMENTAL);
            deviceInfo.put("VERSION.SDK.NUMBER", Build.VERSION.SDK_INT);
//            deviceInfo.put("BOARD", Build.BOARD);
//            deviceInfo.put("BOOTLOADER", Build.BOOTLOADER);
            deviceInfo.put("BRAND", Build.BRAND);
            deviceInfo.put("CPU_ABI", Build.CPU_ABI);
//            deviceInfo.put("CPU_ABI2", Build.CPU_ABI2);
//            deviceInfo.put("DISPLAY", Build.DISPLAY);
//            deviceInfo.put("FINGERPRINT", Build.FINGERPRINT);
//            deviceInfo.put("HARDWARE", Build.HARDWARE);
//            deviceInfo.put("HOST", Build.HOST);
            deviceInfo.put("ID", Build.ID);
            deviceInfo.put("MANUFACTURER", Build.MANUFACTURER);
            deviceInfo.put("MODEL", Build.MODEL);
            deviceInfo.put("PRODUCT", Build.PRODUCT);
            deviceInfo.put("SERIAL", Build.SERIAL);
//            deviceInfo.put("TIME", Build.TIME);
//            deviceInfo.put("TAGS", Build.TAGS);
            deviceInfo.put("TYPE", Build.TYPE);
//            deviceInfo.put("UNKNOWN", Build.UNKNOWN);
//            deviceInfo.put("USER", Build.USER);
            deviceInfo.put("IP Address", NetworkUtils.getIPAddress(true));

            return deviceInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        /*String str= "Device Information\n"
                + "\n DEVICE.ID : " + getDeviceId(context)
                + "\n USER.ID : " + getUserIdentity(context)
                + "\n APP.VERSION : " + getAppVersion(context)
                + "\n LAUNCHER.APP : " + getCurrentLauncherApp(context)
                + "\n TIMEZONE : " + timeZone()
                + "\n VERSION.RELEASE : " + Build.VERSION.RELEASE
                + "\n VERSION.INCREMENTAL : " + Build.VERSION.INCREMENTAL
                + "\n VERSION.SDK.NUMBER : " + Build.VERSION.SDK_INT
                + "\n BOARD : " + Build.BOARD
                + "\n BOOTLOADER : " + Build.BOOTLOADER
                + "\n BRAND : " + Build.BRAND
                + "\n CPU_ABI : " + Build.CPU_ABI
                + "\n CPU_ABI2 : " + Build.CPU_ABI2
                + "\n DISPLAY : " + Build.DISPLAY
                + "\n FINGERPRINT : " + Build.FINGERPRINT
                + "\n HARDWARE : " + Build.HARDWARE
                + "\n HOST : " + Build.HOST
                + "\n ID : " + Build.ID
                + "\n MANUFACTURER : " + Build.MANUFACTURER
                + "\n MODEL : " + Build.MODEL
                + "\n PRODUCT : " + Build.PRODUCT
                + "\n SERIAL : " + Build.SERIAL
                + "\n TAGS : " + Build.TAGS
                + "\n TIME : " + Build.TIME
                + "\n TYPE : " + Build.TYPE
                + "\n UNKNOWN : " + Build.UNKNOWN
                + "\n USER : " + Build.USER
                + "\n IP Address : " + NetworkUtils.getIPAddress(true);*/

    }

    public static String timeZone() {
        TimeZone tz = TimeZone.getDefault();
        return tz.getID();
    }

    public static String getDeviceId(Context context) {
        String androidDeviceId = getAndroidDeviceId(context);
        if (androidDeviceId == null)
            androidDeviceId = UUID.randomUUID().toString();
        return androidDeviceId;

    }

    public static String getAndroidDeviceId(Context context) {
        final String INVALID_ANDROID_ID = "9774d56d682e549c";
        final String androidId = android.provider.Settings.Secure.getString(
                context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        if (androidId == null
                || androidId.toLowerCase().equals(INVALID_ANDROID_ID)) {
            return null;
        }
        return androidId;
    }

    public static String getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode + "(" + packageInfo.versionName + ")";
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
}
