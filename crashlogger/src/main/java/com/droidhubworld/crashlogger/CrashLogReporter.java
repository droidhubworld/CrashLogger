package com.droidhubworld.crashlogger;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.droidhubworld.crashlogger.activity.CrashLoggerActivity;
import com.droidhubworld.crashlogger.utils.CrashLogExceptionHandler;
import com.droidhubworld.crashlogger.utils.CrashLogNotInitializedException;
import com.droidhubworld.crashlogger.utils.CrashLogUtil;

public class CrashLogReporter {
    private static Context applicationContext;

    private static String crashReportPath;

    private static boolean isNotificationEnabled = true;


    private CrashLogReporter() {
        // This class in not publicly instantiable
    }

    public static void initialize(Context context) {
        applicationContext = context;
        setUpExceptionHandler();
    }

    public static void initialize(Context context, String crashReportSavePath) {
        applicationContext = context;
        crashReportPath = crashReportSavePath;
        setUpExceptionHandler();
    }

    private static void setUpExceptionHandler() {
        if (!(Thread.getDefaultUncaughtExceptionHandler() instanceof CrashLogExceptionHandler)) {
            Thread.setDefaultUncaughtExceptionHandler(new CrashLogExceptionHandler());
        }
    }

    public static Context getContext() {
        if (applicationContext == null) {
            try {
                throw new CrashLogNotInitializedException("Initialize CrashLogger : call CrashLogger.initialize(context, crashLoggerPath)");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return applicationContext;
    }

    public static String getCrashReportPath() {
        return crashReportPath;
    }

    public static boolean isNotificationEnabled() {
        return isNotificationEnabled;
    }

    //LOG Exception APIs
    public static void logException(Exception exception) {
        CrashLogUtil.logException(exception);
    }

    public static void logException(View view, Exception exception) {
        CrashLogUtil.logException(exception);
        CrashLogUtil.takeScreenshot(view);
    }

    public static Intent getLaunchIntent() {
        return new Intent(applicationContext, CrashLoggerActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public static void disableNotification() {
        isNotificationEnabled = false;
    }
}
