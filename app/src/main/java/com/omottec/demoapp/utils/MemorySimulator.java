package com.omottec.demoapp.utils;

import android.os.SystemClock;
import android.util.Log;

import com.omottec.demoapp.Tag;

import java.util.Arrays;
import java.util.concurrent.Executors;

/**
 * Created by qinbingbing on 4/5/16.
 * adb shell am kill [--user <USER_ID> | all | current] <PACKAGE>
 * adb shell pm list packages
 */
public final class MemorySimulator {
    private MemorySimulator() {
    }

    public static void asyncDrainMemoryDouble(long internal) {
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                drainMemoryDouble(internal);
            }
        });
    }

    public static void asyncDrainMemorySlow(final long internal) {
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                drainMemorySlow(internal);
            }
        });
    }

    public static void drainMemoryDouble(long internal) {
        byte[] bytes = new byte[1 * 1024 * 1024];
        Log.d(Tag.MEMORY, "alloc memory " + bytes.length / 1024 / 1024 + "M");
        for (int i = 0; i <= bytes.length; i++) {
            if (i == bytes.length) {
                SystemClock.sleep(internal);
                bytes = Arrays.copyOf(bytes, bytes.length * 2);
                Log.d(Tag.MEMORY, "alloc memory " + bytes.length / 1024 / 1024 + "M");
            }
            bytes[i] = 1;
        }
    }

    public static void drainMemorySlow(long internal) {
        byte[] bytes = new byte[1 * 1024 * 1024];
        Log.d(Tag.MEMORY, "alloc memory " + bytes.length / 1024 / 1024 + "M");
        for (int i = 0; i <= bytes.length; i++) {
            if (i == bytes.length) {
                SystemClock.sleep(internal);
                bytes = Arrays.copyOf(bytes, bytes.length + 1 * 1024 * 1024);
                Log.d(Tag.MEMORY, "alloc memory " + bytes.length / 1024 / 1024 + "M");
            }
            bytes[i] = 1;
        }
    }
}

