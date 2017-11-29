package com.omottec.demoapp.app.status;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.omottec.demoapp.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinbingbing on 16/11/2017.
 */

public class AppStatusHelper implements Application.ActivityLifecycleCallbacks {
    private Activity mResumedActivity;
    private int mStartedCount;
    private boolean mBackground = true;
    private List<AppStatusListener> mAppStatusListeners = new ArrayList<>();

    private AppStatusHelper() {}

    private static class SingletonHolder {
        private static final AppStatusHelper SINGLETON = new AppStatusHelper();
    }

    public static AppStatusHelper getInstance() {
        return SingletonHolder.SINGLETON;
    }

    public Activity getResumedActivity() {
        return mResumedActivity;
    }

    public boolean isBackground() { return mBackground; }

    public void registerListener(AppStatusListener listener) {
        mAppStatusListeners.add(listener);
    }

    public void unregisterListener(AppStatusListener listener) {
        mAppStatusListeners.remove(listener);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d(Tag.ACTIVITY_LIFECYCLE, "onActivityCreated " + activity.toString());
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.d(Tag.ACTIVITY_LIFECYCLE, "onActivityStarted " + activity.toString());
        if (mStartedCount <= 0) {
            mStartedCount = 0;
            mBackground = false;
            Log.d(Tag.ACTIVITY_LIFECYCLE, "onEnterForeground");
            for (AppStatusListener listener : mAppStatusListeners)
                listener.onEnterForeground();
        }
        mStartedCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.d(Tag.ACTIVITY_LIFECYCLE, "onActivityResumed " + activity.toString());
        mResumedActivity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.d(Tag.ACTIVITY_LIFECYCLE, "onActivityPaused " + activity.toString());
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.d(Tag.ACTIVITY_LIFECYCLE, "onActivityStopped " + activity.toString());
        mStartedCount--;
        if (mStartedCount <= 0) {
            mStartedCount = 0;
            mBackground = true;
            Log.d(Tag.ACTIVITY_LIFECYCLE, "onEnterBackground");
            for(AppStatusListener listener : mAppStatusListeners)
                listener.onEnterBackground();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.d(Tag.ACTIVITY_LIFECYCLE, "onActivitySaveInstanceState " + activity.toString());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.d(Tag.ACTIVITY_LIFECYCLE, "onActivityDestroyed " + activity.toString());
    }
}
