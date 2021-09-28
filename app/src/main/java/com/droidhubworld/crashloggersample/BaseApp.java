package com.droidhubworld.crashloggersample;

import android.app.Application;
import android.os.Environment;

import com.droidhubworld.crashlogger.CrashLogReporter;

import java.io.File;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            //initialise reporter with external path
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CrashLogger";

            new CrashLogReporter.Builder(this)
                    .crashReportPath("CrashLogger")
                    .crashReportFileName("crash_log")
                    .isNotificationEnabled(false)
                    .build();
        }
    }
}