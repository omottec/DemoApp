package com.omottec.demoapp.utils;

import android.os.SystemClock;
import android.util.Log;

import java.util.Arrays;

/**
 * Created by qinbingbing on 16/11/2017.
 */

public class ClickUtils {
    private static final String TAG = "ClickUtils";

    private ClickUtils() {}

    private static long lastClickTime;

    private static final long[] HITS = new long[5];

    public static boolean isFastDoubleClick() {
        long newClickTime = SystemClock.elapsedRealtime();
        if (newClickTime - lastClickTime < 300) {
            return true;
        } else {
            lastClickTime = newClickTime;
            return false;
        }
    }

    public static boolean isFastFiveClick() {
        System.arraycopy(HITS, 1, HITS, 0, HITS.length-1);
        HITS[HITS.length-1] = SystemClock.elapsedRealtime();
        Log.d(TAG, "HITS:" + Arrays.toString(HITS));
        return SystemClock.elapsedRealtime() - HITS[0] < 800;
    }
}
