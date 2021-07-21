package com.droidhubworld.crashlogger.utils;

public class CrashLoggerException extends RuntimeException {
    static final long serialVersionUID = 1;

    /**
     * Constructs a new CrashLoggerException.
     */
    public CrashLoggerException() {
        super();
    }

    /**
     * Constructs a new CrashLoggerException.
     *
     * @param message the detail message of this exception
     */
    public CrashLoggerException(String message) {
        super(message);
    }

    /**
     * Constructs a new CrashLoggerException.
     *
     * @param format the format string (see {@link java.util.Formatter#format})
     * @param args   the list of arguments passed to the formatter.
     */
    public CrashLoggerException(String format, Object... args) {
        this(String.format(format, args));
    }

    /**
     * Constructs a new CrashLoggerException.
     *
     * @param message   the detail message of this exception
     * @param throwable the cause of this exception
     */
    public CrashLoggerException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a new CrashLoggerException.
     *
     * @param throwable the cause of this exception
     */
    public CrashLoggerException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String toString() {
        // Throwable.toString() returns "CrashLoggerException:{message}". Returning just "{message}"
        // should be fine here.
        return getMessage();
    }
}