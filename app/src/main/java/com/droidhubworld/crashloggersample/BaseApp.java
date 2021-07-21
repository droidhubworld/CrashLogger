package com.droidhubworld.crashloggersample;

import android.app.Application;

import com.droidhubworld.crashlogger.CrashLogReporter;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            //initialise reporter with external path
            CrashLogReporter.initialize(this);
        }
    }
}