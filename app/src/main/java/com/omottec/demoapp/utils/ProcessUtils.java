package com.omottec.demoapp.utils;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class ProcessUtils {
    private static final String TAG = "ProcessUtils";
    private static String sProcessName;
    private static boolean sMainProcess;

    public static void testApi(Context context) {
        String processNameFromActivityThread = getProcessNameFromActivityThread(context);
        String processNameFromFile = getProcessNameFromFile();
        String processNameFromAms = getProcessNameFromAms(context);
        Log.i(TAG, "processNameFromActivityThread:" + processNameFromActivityThread
            + ", processNameFromFile:" + processNameFromFile
            + ", processNameFromAms:" + processNameFromAms);
    }

    public static String getProcessName(Context context) {
        if (!TextUtils.isEmpty(sProcessName)) {
            return sProcessName;
        }

        sProcessName = getProcessNameFromActivityThread(context);
        if (!TextUtils.isEmpty(sProcessName)) {
            return sProcessName;
        }

        sProcessName = getProcessNameFromFile();
        if (!TextUtils.isEmpty(sProcessName)) {
            return sProcessName;
        }

        sProcessName = getProcessNameFromAms(context);
        return sProcessName;
    }

    public static boolean isInMainProcess(Context context) {
        if (sMainProcess) return sMainProcess;

        sProcessName = ProcessUtils.getProcessName(context);
        if (!TextUtils.isEmpty(sProcessName) && sProcessName.indexOf(":") == -1)
            sMainProcess = true;
        return sMainProcess;
    }

    public static String getProcessNamePort(Context context) {
        if (isInMainProcess(context)) {
            return "";
        }

        if (TextUtils.isEmpty(sProcessName)) {
            return "unknown";
        }

        int index = sProcessName.indexOf(":");
        if (index != -1) {
            return sProcessName.substring(index + 1);
        } else {
            return "unknown";
        }
    }

    private static String getProcessNameFromFile() {
        File cmdFile = new File("/proc/self/cmdline");
        if (!cmdFile.exists() || cmdFile.isDirectory()) return "";
        BufferedReader reader = null;
        String processName = "";
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(cmdFile)));
            String procName = reader.readLine();
            if (!TextUtils.isEmpty(procName))
                processName = procName.trim();
        } catch (Exception e) {
            Log.i(TAG, "read /proc/self/cmdline exception", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return processName;
    }

    private static String getProcessNameFromActivityThread(Context context) {
        String processName = "";
        try {
            Application app = (Application) context;
            Field loadedApkField = app.getClass().getField("mLoadedApk");
            loadedApkField.setAccessible(true);
            Object loadedApk = loadedApkField.get(app);
            Log.i(TAG, "loadedApk:" + loadedApk);

            Field activityThreadField = loadedApk.getClass().getDeclaredField("mActivityThread");
            activityThreadField.setAccessible(true);
            Object activityThread = activityThreadField.get(loadedApk);

            Method getProcessName = activityThread.getClass().getDeclaredMethod("getProcessName");
            processName = (String) getProcessName.invoke(activityThread);
        } catch (Exception e) {
            Log.e(TAG, "Application.LoadedApk.ActivityThread#getProcessName", e);
        }
        return processName;
    }

    private static String getProcessNameFromAms(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return "";
        List<ActivityManager.RunningAppProcessInfo> appProcessInfoList = am.getRunningAppProcesses();
        if (appProcessInfoList == null) return "";
        for (ActivityManager.RunningAppProcessInfo processInfo : appProcessInfoList) {
            if (processInfo.pid == android.os.Process.myPid()) {
                return processInfo.processName.trim();
            }
        }
        return "";
    }
}