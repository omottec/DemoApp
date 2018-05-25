package com.omottec.demoapp.app;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.os.Handler;
import android.os.Process;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.app.status.AppStatusHelper;
import com.omottec.demoapp.app.status.AppStatusListener;
import com.omottec.demoapp.fresco.ImagePipelineConfigFactory;
import com.omottec.demoapp.memory.MemoryUtils;
import com.omottec.demoapp.utils.AppUtils;
import com.omottec.demoapp.utils.Logger;
import com.omottec.demoapp.utils.TimeLogger;
import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinbingbing on 3/23/16.
 */
public class MyApplication extends Application {
    public static List<Activity> sLeakActivities = new ArrayList<>();
    private static Context sAppContext;
    private static Handler sUiHandler;
    private boolean mIsMainProcess;

    public static Context getContext() {
        return sAppContext;
    }

    public static Handler getUiHandler() {
        return sUiHandler;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Log.d(Tag.APP_PROCESS, this + " attachBaseContext");
        Log.d(Tag.APP_PROCESS, "mIsMainProcess before call:" + mIsMainProcess);
        mIsMainProcess = AppUtils.isMainProcess(this, Process.myPid());
        Log.d(Tag.APP_PROCESS, "mIsMainProcess after call:" + mIsMainProcess);
    }

    @Override
    public void onCreate() {
        TimeLogger.methodStart();
        super.onCreate();
        Log.d(Tag.TASK, this + " onCreate");
        Log.d(Tag.APP_PROCESS, this + " onCreate");
        Log.d(Tag.APP_PROCESS, "mIsMainProcess:" + mIsMainProcess);
        sAppContext = this;
        sUiHandler = new Handler();
        /*if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }*/
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        Fresco.initialize(this, ImagePipelineConfigFactory.getOkHttpImagePipelineConfig(this));
        registerAppStatusListener();
//        registerComponentCallbacks(this);
        TimeLogger.methodEnd();
    }

    private void registerAppStatusListener() {
        AppStatusHelper.getInstance().registerListener(new AppStatusListener() {
            @Override
            public void onEnterBackground() {
                Logger.d(Tag.MEMORY, "onEnterBackground");
            }

            @Override
            public void onEnterForeground() {
                Logger.d(Tag.MEMORY, "onEnterForeground");
            }
        });
        registerActivityLifecycleCallbacks(AppStatusHelper.getInstance());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(Tag.MEMORY, this + " onLowMemory");
//        Log.d(Tag.FRESCO, this + " onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.d(Tag.MEMORY, this + " onTrimMemory level:" + MemoryUtils.getTrimMemoryLevel(level));
//        Log.d(Tag.FRESCO, this + " onTrimMemory level:" + level);

        // Determine which lifecycle or system event was raised.
        switch (level) {

            case ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN:

                /*
                   Release any UI objects that currently hold memory.

                   The user interface has moved to the background.
                */

                break;

            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE:
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW:
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL:

                /*
                   Release any memory that your app doesn't need to run.

                   The device is running low on memory while the app is running.
                   The event raised indicates the severity of the memory-related event.
                   If the event is TRIM_MEMORY_RUNNING_CRITICAL, then the system will
                   begin killing background processes.
                */

                break;

            case ComponentCallbacks2.TRIM_MEMORY_BACKGROUND:
            case ComponentCallbacks2.TRIM_MEMORY_MODERATE:
            case ComponentCallbacks2.TRIM_MEMORY_COMPLETE:

                /*
                   Release as much memory as the process can.

                   The app is on the LRU list and the system is running low on memory.
                   The event raised indicates where the app sits within the LRU list.
                   If the event is TRIM_MEMORY_COMPLETE, the process will be one of
                   the first to be terminated.
                */

                break;

            default:
                /*
                  Release any non-critical data structures.

                  The app received an unrecognized memory level value
                  from the system. Treat this as a generic low-memory message.
                */
                break;
        }
    }
}
