package com.omottec.demoapp.utils;

import android.os.Looper;
import android.util.Log;

/**
 * Created by qinbingbing on 9/8/16.
 */
public final class Logger {
    private Logger() {}

    public static void logIsUiThread(String tag) {
        Log.d(tag, "is uiThread:" + (Looper.myLooper() == Looper.getMainLooper()));
    }
}
