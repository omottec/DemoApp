package com.omottec.demoapp.task;

import android.app.ActivityManager;
import android.content.Context;

import com.omottec.demoapp.Tag;
import com.omottec.demoapp.app.MyApplication;
import com.omottec.demoapp.utils.Logger;

import java.util.List;

/**
 * Created by qinbingbing on 28/12/2017.
 */

public final class TaskUtils {
    private TaskUtils() {}

    public static void logTaskActivity() {
        ActivityManager am = (ActivityManager) MyApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return;
        List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(Integer.MAX_VALUE);
        if (runningTasks == null || runningTasks.isEmpty()) return;
        for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
            Logger.d(Tag.TASK, runningTaskInfo
                    + " id:" + runningTaskInfo.id
                    + ", baseActivity:" + runningTaskInfo.baseActivity
                    + ", topActivity:" + runningTaskInfo.topActivity
                    + ", numActivities:" + runningTaskInfo.numActivities
                    + ", numRunning:" + runningTaskInfo.numRunning
                    );
        }
    }

}
