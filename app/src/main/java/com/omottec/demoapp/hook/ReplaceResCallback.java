package com.omottec.demoapp.hook;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import androidx.core.view.LayoutInflaterCompat;
import com.omottec.demoapp.Tag;
import com.omottec.logger.Logger;

public class ReplaceResCallback implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        LayoutInflater inflater = activity.getLayoutInflater();
        Logger.i(Tag.REPLACE_RES, Logger.getInflaterInfo(inflater));
        Logger.i(Tag.REPLACE_RES, "getResources:" + activity.getResources());
        LayoutInflater.Factory2 factory2 = inflater.getFactory2();
        if (factory2 != null) {
            return;
        }
        LayoutInflaterCompat.setFactory2(inflater, new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(View parent,
                                     String name,
                                     Context context,
                                     AttributeSet attrs) {
                Logger.i(Tag.REPLACE_RES, "parent:" + parent
                    + ", name:" + name
                    + ", context:" + context
                    + ", attrs:" + attrs);
                int attributeCount = attrs.getAttributeCount();
                for (int i = 0; i < attributeCount; i++) {
                    Logger.i(Tag.REPLACE_RES, "attributeName:" + attrs.getAttributeName(i)
                        + ", attributeValue:" + attrs.getAttributeValue(i));
                }
                Resources resources = activity.getResources();
                Logger.i(Tag.REPLACE_RES, "");
                return null;
            }

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                Logger.i(Tag.REPLACE_RES, "name:" + name
                    + ", context:" + context
                    + ", attrs:" + attrs);
                return null;
            }
        });
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

    }
}
