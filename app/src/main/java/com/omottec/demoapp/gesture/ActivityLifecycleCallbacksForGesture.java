package com.omottec.demoapp.gesture;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.omottec.demoapp.Tag;
import com.omottec.demoapp.app.MyApplication;
import com.omottec.logger.Logger;

/**
 * Created by qinbingbing on 07/12/2018.
 */

public class ActivityLifecycleCallbacksForGesture implements Application.ActivityLifecycleCallbacks {
    private View.OnTouchListener mTouchListener;
    private GestureDetector mGestureDetector;

    public ActivityLifecycleCallbacksForGesture() {
        mGestureDetector = new GestureDetector(MyApplication.getContext(), new MySimpleOnGestureListener());
        mTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return false;
            }
        };
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Logger.d(Tag.GESTURE, "onActivityCreated " + activity);
        activity.getWindow().getDecorView().setOnTouchListener(mTouchListener);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Logger.d(Tag.GESTURE, "onActivityDestroyed " + activity);
        activity.getWindow().getDecorView().setOnTouchListener(null);
    }
}
