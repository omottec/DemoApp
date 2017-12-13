package com.omottec.demoapp.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import com.omottec.demoapp.Tag;

import java.util.List;

/**
 * Created by qinbingbing on 8/18/16.
 */
public class AppUtils {
    private AppUtils() {}

    public static boolean isMainProcess(Context context, int pid) {
        try {
            String packageName = context.getPackageName();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            if (runningAppProcesses == null) return false;
            for (ActivityManager.RunningAppProcessInfo info : runningAppProcesses) {
                Log.d(Tag.APP_PROCESS, "context: " + context
                        + ", packageName:" + packageName
                        + ", pid:" + pid
                        + ", info.processName:" + info.processName
                        + ", info.pid:" + info.pid);
                if (packageName.equals(info.processName))
                    return info.pid == pid;
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            // java.lang.RuntimeException: Package manager has died
            return false;
        }
    }


}
