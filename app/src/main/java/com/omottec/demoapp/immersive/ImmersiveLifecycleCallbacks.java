package com.omottec.demoapp.immersive;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.UiUtils;
import com.omottec.logger.Logger;

/**
 * Created by qinbingbing on 12/07/2018.
 */

public class ImmersiveLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Logger.d(Tag.IMMERSIVE, "onActivityCreated " + activity);
        int sdkInt = Build.VERSION.SDK_INT;
        String release = Build.VERSION.RELEASE;
        String brand = Build.BRAND;
        int statusBarHeight = UiUtils.getStatusBarHeight(activity);
        Logger.d(Tag.IMMERSIVE, "sdkInt:" + sdkInt
                + ", release:" + release
                + ", brand:" + brand
                + ", statusBarHeight:" + statusBarHeight);
        if (activity instanceof ImmersiveActivity && sdkInt >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

//            View decorView = activity.getWindow().getDecorView();
//            int sysUiFlag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(sysUiFlag);
//            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);

            ViewGroup contentView = window.findViewById(Window.ID_ANDROID_CONTENT);
            View childView = contentView.getChildAt(0);
            Logger.d(Tag.IMMERSIVE, "contentView:" + contentView
                    + ", childView:" + childView);
            if (childView != null) {
                childView.setFitsSystemWindows(true);
            } else {
                contentView.setFitsSystemWindows(true);
            }
        }
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
