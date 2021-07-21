package com.droidhubworld.crashlogger.utils;

public class CrashLogNotInitializedException extends CrashLoggerException {
    static final long serialVersionUID = 1;

    /**
     * Constructs a CrashLogNotInitializedException with no additional information.
     */
    public CrashLogNotInitializedException() {
        super();
    }

    /**
     * Constructs a CrashLogNotInitializedException with a message.
     *
     * @param message A String to be returned from getMessage.
     */
    public CrashLogNotInitializedException(String message) {
        super(message);
    }

    /**
     * Constructs a CrashLogNotInitializedException with a message and inner error.
     *
     * @param message   A String to be returned from getMessage.
     * @param throwable A Throwable to be returned from getCause.
     */
    public CrashLogNotInitializedException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a CrashLogNotInitializedException with an inner error.
     *
     * @param throwable A Throwable to be returned from getCause.
     */
    public CrashLogNotInitializedException(Throwable throwable) {
        super(throwable);
    }
}