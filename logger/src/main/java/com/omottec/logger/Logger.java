package com.omottec.logger;

import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.view.LayoutInflater;

/**
 * Created by qinbingbing on 9/8/16.
 */
public final class Logger {
    public static final String VERSION = "0.1.4";

    private static int sLevel = Log.VERBOSE;

    private static boolean sAppendCallInfo = false;

    private Logger() {}

    public static void setLevel(int level) {
        sLevel = level;
    }

    public static void appendCallInfo(boolean append) {
        sAppendCallInfo = append;
    }

    public static String getInflaterInfo(LayoutInflater inflater) {
        return new StringBuilder("inflater:").append(inflater)
            .append(", factory:").append(inflater.getFactory())
            .append(", factory2:").append(inflater.getFactory2())
            .append(", context:").append(inflater.getContext())
            .append(", context.getResources:").append(inflater.getContext().getResources())
            .toString();
    }

    public static void logThread(String tag) {
        Log.d(tag, getThreadInfo());
    }

    public static String getThreadInfo() {
        return "pid-tid-uiThread: "
                + Process.myPid() + "-"
                + Process.myTid() + "-"
                + (Looper.myLooper() == Looper.getMainLooper() ? "uiThread" : "workThread");
    }

    public static void logClassAndMethod(Object object) {
        StringBuilder sb = new StringBuilder();
        sb.append(object.hashCode());
        StackTraceElement[] elements = new Throwable().fillInStackTrace().getStackTrace();
        if (elements != null && elements.length > 1 && elements[1] != null)
            sb.append("|").append(elements[1].getMethodName());
        d(object.getClass().getSimpleName(), sb.toString());
    }

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

    public static int i(String tag) {
        if (sLevel > Log.INFO) return 0;
        StringBuilder logBuilder = getCallInfo();
        return Log.i(tag, logBuilder.toString());
    }

    /**
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int i(String tag, String msg) {
        if (sLevel > Log.INFO) return 0;
        if (!sAppendCallInfo) {
            return Log.i(tag, msg);
        } else {
            StringBuilder logBuilder = getCallInfo();
            logBuilder.append("-").append(msg);
            return Log.i(tag, logBuilder.toString());
        }
    }

    /**
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static int i(String tag, String msg, Throwable tr) {
        if (sLevel > Log.INFO) return 0;
        if (!sAppendCallInfo) {
            return Log.i(tag, msg, tr);
        } else {
            StringBuilder logBuilder = getCallInfo();
            logBuilder.append("-").append(msg);
            return Log.i(tag, logBuilder.toString(), tr);
        }
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
        if (!sAppendCallInfo) {
            return Log.e(tag, msg);
        } else {
            StringBuilder logBuilder = getCallInfo();
            logBuilder.append("-").append(msg);
            return Log.e(tag, logBuilder.toString());
        }
    }

    /**
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static int e(String tag, String msg, Throwable tr) {
        if (sLevel > Log.ERROR) return 0;
        if (!sAppendCallInfo) {
            return Log.e(tag, msg, tr);
        } else {
            StringBuilder logBuilder = getCallInfo();
            logBuilder.append("-").append(msg);
            return Log.e(tag, logBuilder.toString(), tr);
        }
    }

    public static int e(String tag, Throwable tr) {
        if (sLevel > Log.ERROR) return 0;
        if (!sAppendCallInfo) {
            return Log.e(tag, "", tr);
        } else {
            StringBuilder logBuilder = getCallInfo();
            return Log.e(tag, logBuilder.toString(), tr);
        }
    }

    private static StringBuilder getCallInfo() {
        StringBuilder builder = new StringBuilder()
            .append(Process.myPid())
            .append("-").append(Process.myTid())
            .append("-").append(Thread.currentThread().getName());
        StackTraceElement[] stackTrace = new Throwable().fillInStackTrace().getStackTrace();
        if (stackTrace != null & stackTrace.length > 2 & stackTrace[2] != null) {
            int index = stackTrace[2].getClassName().lastIndexOf('.');
            builder.append("-").append(stackTrace[2].getClassName().substring(index + 1))
                .append("-").append(stackTrace[2].getMethodName())
                .append('-').append(stackTrace[2].getLineNumber());
        }
        return builder;
    }
}
