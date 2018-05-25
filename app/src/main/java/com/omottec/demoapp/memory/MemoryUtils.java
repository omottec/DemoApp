package com.omottec.demoapp.memory;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.os.Build;
import android.os.Debug;

import com.omottec.demoapp.app.MyApplication;

/**
 * Created by qinbingbing on 09/02/2018.
 */

public final class MemoryUtils {
    public static final int MEGA_BYTES = 1 * 1024 * 1024;

    private MemoryUtils() {}

    public static String getMemoryInfo() {
        ActivityManager am = (ActivityManager) MyApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        int memoryClass = am.getMemoryClass();
        int largeMemoryClass = am.getLargeMemoryClass();
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memoryInfo);
        long maxMemory = Runtime.getRuntime().maxMemory() / MEGA_BYTES;
        long totalMemory = Runtime.getRuntime().totalMemory() / MEGA_BYTES;
        long freeMemory = Runtime.getRuntime().freeMemory() / MEGA_BYTES;

        String debugMemInfo = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Debug.MemoryInfo memInfo = new Debug.MemoryInfo();
            Debug.getMemoryInfo(memInfo);
            debugMemInfo = memInfo.getMemoryStats().toString();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("ActivityManager.getMemoryClass(): ").append(memoryClass)
                .append("\nActivityManager.getLargeMemoryClass(): ").append(largeMemoryClass)
                .append("\nActivityManager.MemoryInfo.availMem: ").append(memoryInfo.availMem / MEGA_BYTES)
                .append("\nActivityManager.MemoryInfo.totalMem: ").append(memoryInfo.totalMem / MEGA_BYTES)
                .append("\nActivityManager.MemoryInfo.threshold: ").append(memoryInfo.threshold / MEGA_BYTES)
                .append("\nActivityManager.MemoryInfo.lowMemory: ").append(memoryInfo.lowMemory)
                .append("\nRuntime.getRuntime().maxMemory(): ").append(maxMemory)
                .append("\nRuntime.getRuntime().totalMemory(): ").append(totalMemory)
                .append("\nRuntime.getRuntime().freeMemory(): ").append(freeMemory)
                .append("\nDebug.MemoryInfo.getMemoryStats: ").append(debugMemInfo);
        return sb.toString();
    }

    public static String getTrimMemoryLevel(int level) {
        switch (level) {
            case ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN:
                return "TRIM_MEMORY_UI_HIDDEN";
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE:
                return "TRIM_MEMORY_RUNNING_MODERATE";
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW:
                return "TRIM_MEMORY_RUNNING_LOW";
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL:
                return "TRIM_MEMORY_RUNNING_CRITICAL";
            case ComponentCallbacks2.TRIM_MEMORY_BACKGROUND:
                return "TRIM_MEMORY_BACKGROUND";
            case ComponentCallbacks2.TRIM_MEMORY_MODERATE:
                return "TRIM_MEMORY_MODERATE";
            case ComponentCallbacks2.TRIM_MEMORY_COMPLETE:
                return "TRIM_MEMORY_COMPLETE";
            default:
                return "UNKNOWN";
        }
    }
}
