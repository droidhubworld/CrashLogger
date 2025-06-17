package com.droidhubworld.crashlogger.utils;

import android.util.Log;

//import com.droidhubworld.crashlogger.BuildConfig;

public class Logger {
    public static void e(String TAG, String message) {
//        if (BuildConfig.DEBUG) {
            Log.e(TAG, message);
//        }
    }

    public static void d(String TAG, String message) {
//        if (BuildConfig.DEBUG) {
            Log.d(TAG, message);
//        }
    }
}
