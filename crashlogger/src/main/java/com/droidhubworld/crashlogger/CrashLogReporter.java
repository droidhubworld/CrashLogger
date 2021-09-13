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
    private static String crashReportFileName;

    private static boolean isNotificationEnabled = true;


    private CrashLogReporter() {
        // This class in not publicly instantiable
    }

    public static void initialize(Context context) {
        applicationContext = context;
        setUpExceptionHandler();
    }

    public CrashLogReporter(Context context, String crashReportSavePath, String crashReportSaveFileName) {
        applicationContext = context;
        crashReportPath = crashReportSavePath;
        crashReportFileName = crashReportSaveFileName;
        setUpExceptionHandler();
    }

    public static class Builder {
        Context applicationContext;
        String crashReportPath;
        String crashReportFileName;
        boolean isNotificationEnabled;

        public Builder(Context mContext) {
            applicationContext = mContext;
        }

        public Builder crashReportPath(String crashReportPath) {
            this.crashReportPath = crashReportPath;
            return this;
        }

        public Builder crashReportFileName(String crashReportFileName) {
            this.crashReportFileName = crashReportFileName;
            return this;
        }

        public Builder isNotificationEnabled(Boolean isNotificationEnabled) {
            this.isNotificationEnabled = isNotificationEnabled;
            return this;
        }


        public CrashLogReporter build() {
            return new CrashLogReporter(applicationContext, crashReportPath, crashReportFileName);
        }
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

    public static String getCrashReportFileName() {
        return crashReportFileName;
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
        if (view != null)
            CrashLogUtil.takeScreenshot(view);
    }

    public static void logReadAndWriteException(View view, Exception exception) {
        CrashLogUtil.readAndWrite(exception);
        if (view != null)
            CrashLogUtil.takeScreenshot(view);
    }
    public static void logReadAndWriteException(View view, Throwable exception) {
        CrashLogUtil.readAndWrite(exception);
        if (view != null)
            CrashLogUtil.takeScreenshot(view);
    }

    public static void logReadAndWriteException(View view, String TAG, Exception exception) {
        CrashLogUtil.readAndWrite(TAG, exception);
        if (view != null)
            CrashLogUtil.takeScreenshot(view);
    }
    public static void logReadAndWriteException(View view, String TAG, Throwable exception) {
        CrashLogUtil.readAndWrite(TAG, exception);
        if (view != null)
            CrashLogUtil.takeScreenshot(view);
    }

    public static void logReadAndWriteException(View view, String TAG, String folderPath, String fileName, String text) {
        CrashLogUtil.readAndWrite(text, TAG, folderPath, fileName);
        if (view != null)
            CrashLogUtil.takeScreenshot(view);
    }

    public static Intent getLaunchIntent() {
        return new Intent(applicationContext, CrashLoggerActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public static void disableNotification() {
        isNotificationEnabled = false;
    }
}
