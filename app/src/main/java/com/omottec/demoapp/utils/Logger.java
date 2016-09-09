package com.omottec.demoapp.utils;

import android.os.Looper;
import android.util.Log;

/**
 * Created by qinbingbing on 9/8/16.
 */
public final class Logger {
    private Logger() {}

    public static void logThread(String tag) {
        Log.d(tag, Thread.currentThread().getName()
                + ", is uiThread:" + (Looper.myLooper() == Looper.getMainLooper()));
    }
}
