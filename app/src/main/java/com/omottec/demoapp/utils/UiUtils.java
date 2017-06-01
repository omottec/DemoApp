package com.omottec.demoapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * Created by qinbingbing on 4/7/16.
 */
public final class UiUtils {
    private UiUtils() { throw new AssertionError(); }

    /**
     * 获取虚拟按键栏高度
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (hasNavBar(context)){
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 检查是否存在虚拟按键栏
     * @param context
     * @return
     */
    private static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     * @return
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }

    /**
     * 获取屏幕宽高
     *
     * @param context
     * @param isWidth
     *            true:获取宽度 false:获取高度
     * @return
     */
    public static int getScreenSize(Context context, boolean isWidth) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        int temp = 0;
        if (isWidth) {
            temp = dm.widthPixels;
        } else {
            temp = dm.heightPixels;
        }
        return temp;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scaledDensity + 0.5);
    }

    public static int px2sp(Context context, float pxValue) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scaledDensity + 0.5);
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        if (context == null) {
            return 0;
        }
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
//        return context.getResources().getDimensionPixelSize(com.android.internal.R.dimen.status_bar_height);
    }

    public static int getStatusBarHeight1(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    public static String getMeasureSpecMode(int measureSpec) {
        switch (View.MeasureSpec.getMode(measureSpec)) {
            case View.MeasureSpec.EXACTLY:
                return "MeasureSpec.EXACTLY";
            case View.MeasureSpec.AT_MOST:
                return "MeasureSpec.AT_MOST";
            case View.MeasureSpec.UNSPECIFIED:
                return "MeasureSpec.UNSPECIFIED";
            default:
                return "Unknown";
        }
    }

    public static String getMeasureSpecModeAndSize(int measureSpec) {
        int mode = View.MeasureSpec.getMode(measureSpec);
        int size = View.MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case View.MeasureSpec.EXACTLY:
                return "MeasureSpec.EXACTLY:" + size;
            case View.MeasureSpec.AT_MOST:
                return "MeasureSpec.AT_MOST:" + size;
            case View.MeasureSpec.UNSPECIFIED:
                return "MeasureSpec.UNSPECIFIED:" + size;
            default:
                return "Unknown";
        }
    }
}
