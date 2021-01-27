package com.omottec.demoapp.fragment;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.app.MyApplication;
import com.omottec.demoapp.utils.UiUtils;

import com.omottec.logger.Logger;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Properties;

/**
 * Created by qinbingbing on 31/08/2018.
 */

public class ScreenSizeFragment extends Fragment {
    public static final String TAG = "ScreenSizeFragment";
    private TextView mTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_screen_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Logger.d(TAG, "onViewCreated");
        mTv = view.findViewById(R.id.tv);

        int statusBarHeight = UiUtils.getStatusBarHeight(getContext());
        Logger.d(TAG, "statusBarHeight: " + statusBarHeight);
        int activityContextWidth = UiUtils.getScreenSize(getContext(), true);
        int activityContextHeight = UiUtils.getScreenSize(getContext(), false);
        Logger.d(TAG, "activity context width*height: " + activityContextWidth + "*" + activityContextHeight);
        int width = UiUtils.getScreenSize(MyApplication.getContext(), true);
        int height = UiUtils.getScreenSize(MyApplication.getContext(), false);
        Logger.d(TAG, "app context width*height: " + width + "*" + height);
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();

        Point point = new Point();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getRealSize(point);
        Logger.d(TAG, "point.x:" + point.x + ", point.y:" + point.y);

        float density = displayMetrics.density;
        float scaledDensity = displayMetrics.scaledDensity;
        int densityDpi = displayMetrics.densityDpi;
        Logger.d(TAG, "density:" + density
                + ", scaledDensity:" + scaledDensity
                + ", densityDpi:" + densityDpi);
        double size = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2)) / ((double) densityDpi);
        Logger.d(TAG, "size:" + size);

        float xdpi = displayMetrics.xdpi;
        Logger.d(TAG, "xdpi:" + xdpi);
        float ydpi = displayMetrics.ydpi;
        Logger.d(TAG, "ydpi:" + ydpi);

        float widthInch = width / xdpi;
        Logger.d(TAG, "widthInch:" + widthInch);
        float heightInch = height / ydpi;
        Logger.d(TAG, "heightInch:" + heightInch);
        double doubleScreenSize = Math.sqrt(Math.pow(widthInch, 2) + Math.pow(heightInch, 2));
        Logger.d(TAG, "screenSize double:" + doubleScreenSize);
        double scaleScreenSize = new BigDecimal(doubleScreenSize).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        Logger.d(TAG, "screenSize scale: " + scaleScreenSize);

        String orientation = UiUtils.getOrientation(getContext());
        Logger.d(TAG, "orientation: " + orientation);

        Properties properties = System.getProperties();
        String panelSize = properties.getProperty("ro.boot.mi.panel_size");
        Logger.d(TAG, "properties:" + properties);
        Logger.d(TAG, "model:" + Build.MODEL);
        try {
            Class clazz = Class.forName("android.os.SystemProperties");
            panelSize = (String) clazz.getMethod("get", new Class[]{String.class}).invoke(clazz, "ro.boot.mi.panel_size");
            Logger.d(TAG, "panelSize:" + panelSize);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        String text = new StringBuilder("statusBarHeight:")
                .append(statusBarHeight).append('\n')
                .append("activity context width*height:" + activityContextWidth + "*" + activityContextHeight).append('\n')
                .append("app context width*height:").append(width + "*" + height).append('\n')
                .append("xdpi:").append(xdpi).append(", ydpi:").append(ydpi).append('\n')
                .append("widthInch:").append(widthInch).append(", heightInch:").append(heightInch).append('\n')
                .append("screenSize double:").append(doubleScreenSize).append('\n')
                .append("screenSize scale:").append(scaleScreenSize).append('\n')
                .append("orientation:").append(orientation).append('\n')
                .append("panelSize:").append(panelSize).append('\n')
                .toString();
        mTv.setText(text);
    }
}
