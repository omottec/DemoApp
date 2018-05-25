package com.omottec.demoapp.memory;

import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;

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

        StringBuilder sb = new StringBuilder();
        sb.append("am.getMemoryClass(): ").append(memoryClass)
                .append("\nam.getLargeMemoryClass(): ").append(largeMemoryClass)
                .append("\nmemoryInfo.availMem: ").append(memoryInfo.availMem)
                .append("\nmemoryInfo.totalMem: ").append(memoryInfo.totalMem)
                .append("\nmemoryInfo.threshold: ").append(memoryInfo.threshold)
                .append("\nmemoryInfo.lowMemory: ").append(memoryInfo.lowMemory)
                .append("\nRuntime.getRuntime().maxMemory(): ").append(maxMemory)
                .append("\nRuntime.getRuntime().totalMemory(): ").append(totalMemory)
                .append("\nRuntime.getRuntime().freeMemory(): ").append(freeMemory);
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
