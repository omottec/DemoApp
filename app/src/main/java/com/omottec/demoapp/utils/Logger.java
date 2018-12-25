package com.omottec.demoapp.utils;

import android.os.Build;
import android.os.Looper;
import android.os.Process;
import android.util.Log;

import com.omottec.demoapp.BuildConfig;

/**
 * Created by qinbingbing on 9/8/16.
 */
public final class Logger {
    private Logger() {}

    public static void logThread(String tag) {
        Log.d(tag, "pid-tid-uiThread: "
                + Process.myPid() + "-"
                + Process.myTid() + "-"
                + (Looper.myLooper() == Looper.getMainLooper() ? "uiThread" : "workThread"));
    }

    public static String getThreadInfo() {
        return "pid-tid-uiThread: "
                + Process.myPid() + "-"
                + Process.myTid() + "-"
                + (Looper.myLooper() == Looper.getMainLooper() ? "uiThread" : "workThread");
    }

    private static int sLevel = BuildConfig.DEBUG ? Log.VERBOSE : Log.ASSERT + 1;


    /**
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int v(String tag, String msg) {
        if (sLevel > Log.VERBOSE) return 0;
        return Log.v(tag, msg);
    }

    /**
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static int v(String tag, String msg, Throwable tr) {
        if (sLevel > Log.VERBOSE) return 0;
        return Log.v(tag, msg, tr);
    }

    /**
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int d(String tag, String msg) {
        if (sLevel > Log.DEBUG) return 0;
        return Log.d(tag, msg);
    }

    /**
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static int d(String tag, String msg, Throwable tr) {
        if (sLevel > Log.DEBUG) return 0;
        return Log.d(tag, msg, tr);
    }

    /**
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int i(String tag, String msg) {
        if (sLevel > Log.INFO) return 0;
        return Log.i(tag, msg);
    }

    /**
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static int i(String tag, String msg, Throwable tr) {
        if (sLevel > Log.INFO) return 0;
        return Log.i(tag, msg, tr);
    }

    /**
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int w(String tag, String msg) {
        if (sLevel > Log.WARN) return 0;
        return Log.w(tag, msg);
    }

    /**
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static int w(String tag, String msg, Throwable tr) {
        if (sLevel > Log.WARN) return 0;
        return Log.w(tag, msg, tr);
    }

    /*
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param tr An exception to log
     */
    public static int w(String tag, Throwable tr) {
        if (sLevel > Log.WARN) return 0;
        return Log.w(tag, tr);
    }

    /**
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int e(String tag, String msg) {
        if (sLevel > Log.ERROR) return 0;
        return Log.e(tag, msg);
    }

    /**
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static int e(String tag, String msg, Throwable tr) {
        if (sLevel > Log.ERROR) return 0;
        return Log.e(tag, msg, tr);
    }

    public static void logClassAndMethod(Object object) {
        StringBuilder sb = new StringBuilder();
        sb.append(object.hashCode());
        StackTraceElement[] elements = new Throwable().fillInStackTrace().getStackTrace();
        if (elements != null && elements.length >= 2 && elements[1] != null)
            sb.append("|").append(elements[1].getMethodName());
        d(object.getClass().getSimpleName(), sb.toString());
    }

    public static void setLevel(int level) {
        sLevel = level;
    }
}
