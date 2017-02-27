package com.omottec.demoapp.utils;

import android.os.Looper;
import android.os.Process;
import android.util.Log;

/**
 * Created by qinbingbing on 9/8/16.
 */
public final class Logger {
    private Logger() {}

    public static void logThread(String tag) {
        Log.d(tag, "pid-tid-uiThread: "
                + Process.myPid() + "-"
                + Process.myTid() + "-"
                + (Looper.myLooper() == Looper.getMainLooper()));
    }

    public static String getThreadInfo() {
        return "pid-tid-uiThread: "
                + Process.myPid() + "-"
                + Process.myTid() + "-"
                + (Looper.myLooper() == Looper.getMainLooper());
    }

}
