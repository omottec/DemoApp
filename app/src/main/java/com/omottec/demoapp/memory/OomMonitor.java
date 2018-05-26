package com.omottec.demoapp.memory;

import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by omottec on 30/03/2018.
 */

public final class OomMonitor {
    public static final String TAG = OomMonitor.class.getSimpleName();
    private static OomObserver sObserver;
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final long PERIOD_SECONDS = 3 * 60;

    private OomMonitor() {}

    public static void initialize(OomObserver observer) {
        initialize(observer, PERIOD_SECONDS);
    }
    
    public static void initialize(OomObserver observer, long periodSeconds) {
        sObserver = observer;
        Runnable memoryCalc = new Runnable() {

            @Override
            public void run() {
                long maxMemory = Runtime.getRuntime().maxMemory();
                long totalMemory = Runtime.getRuntime().totalMemory();
                long freeMemory = Runtime.getRuntime().freeMemory();
                float memoryUsage = (totalMemory - freeMemory) * 1.0f / maxMemory;
                StringBuilder memoryInfo = new StringBuilder("maxMemory:").append(maxMemory)
                        .append(", totalMemory:").append(totalMemory)
                        .append(", freeMemory:").append(freeMemory)
                        .append(", memoryUsage:").append(memoryUsage);
                Log.d(TAG, memoryInfo.toString());
                if (sObserver != null) sObserver.memoryUsage(memoryUsage);
            }
        };

        scheduler.scheduleAtFixedRate(memoryCalc, 0, periodSeconds, TimeUnit.SECONDS);
    }
}
