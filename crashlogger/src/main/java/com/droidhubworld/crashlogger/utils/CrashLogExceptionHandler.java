package com.droidhubworld.crashlogger.utils;

public class CrashLogExceptionHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler exceptionHandler;

    public CrashLogExceptionHandler() {
        this.exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

        CrashLogUtil.saveCrashReport(throwable);

        exceptionHandler.uncaughtException(thread, throwable);
    }
}