package com.omottec.demoapp.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.app.fresco.ImagePipelineConfigFactory;
import com.omottec.demoapp.app.status.AppStatusHelper;
import com.omottec.demoapp.utils.AppUtils;
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
//        registerActivityLifecycleCallbacks(AppStatusHelper.getInstance());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(Tag.MEMORY, this + " onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.d(Tag.MEMORY, this + " onTrimMemory level:" + level);
    }
}
