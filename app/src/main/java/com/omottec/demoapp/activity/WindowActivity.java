package com.omottec.demoapp.activity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.TouchUtils;
import com.omottec.demoapp.utils.UiUtils;

import java.util.Arrays;

/**
 * Created by qinbingbing on 9/16/16.
 */
public class WindowActivity extends FragmentActivity implements View.OnTouchListener {
    private TextView mGenWindowTv;
    private WindowManager mWindowManager;
    private TextView mFloatTv;
    private int mStatusBarHeight;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(Tag.WINDOW, "WindowActivity.onConfigurationChanged");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Tag.WINDOW, "WindowActivity.onCreate");
        int height = UiUtils.getScreenSize(this, false);
        Log.d(Tag.WINDOW, "height before setContentView:" + height);
        setContentView(R.layout.a_window);
        height = UiUtils.getScreenSize(this, false);
        Log.d(Tag.WINDOW, "height after setContentView:" + height);
        mStatusBarHeight = UiUtils.getStatusBarHeight(this);
        Log.d(Tag.WINDOW, "statusBarHeight:" + mStatusBarHeight);
        mGenWindowTv = (TextView) findViewById(R.id.gen_window_tv);
        logViewLocation(mGenWindowTv);
        mWindowManager = getWindowManager();
        mGenWindowTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFloatTv = new TextView(WindowActivity.this);
                mFloatTv.setText(mFloatTv.hashCode() + "");
                mFloatTv.setBackgroundColor(Color.GREEN);
                mFloatTv.setGravity(Gravity.CENTER);
                WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                        UiUtils.dip2px(WindowActivity.this, 100),
                        UiUtils.dip2px(WindowActivity.this, 50),
                        0, 0, PixelFormat.TRANSPARENT);
                params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
                params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
                params.gravity = Gravity.LEFT | Gravity.TOP;
                params.x = UiUtils.dip2px(WindowActivity.this, 100);
                params.y = UiUtils.dip2px(WindowActivity.this, 50);
                mWindowManager.addView(mFloatTv, params);
                logViewLocation(mFloatTv);
                mFloatTv.setOnTouchListener(WindowActivity.this);
                mGenWindowTv.setEnabled(false);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Tag.WINDOW, "WindowActivity.onDestroy");
        mWindowManager.removeViewImmediate(mFloatTv);
    }

    private void logViewLocation(View view) {
        int[] locationOnScreen = new int[2];
        view.getLocationOnScreen(locationOnScreen);
        Log.d(Tag.WINDOW, "view.getLocationOnScreen:" + Arrays.toString(locationOnScreen));
        int[] locationInWindow = new int[2];
        view.getLocationInWindow(locationInWindow);
        Log.d(Tag.WINDOW, "view.getLocationInWindow:" + Arrays.toString(locationInWindow));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        float rawX = event.getRawX();
        float rawY = event.getRawY();
        Log.d(Tag.WINDOW, "x:" + x
                + ", y:" + y
                + ", rawX:" + rawX
                + ", rawY:" + rawY);
        Log.d(Tag.WINDOW, TouchUtils.getTouchEventAction(event));
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) mFloatTv.getLayoutParams();
                layoutParams.x = (int) rawX;
                layoutParams.y = (int) (rawY - mStatusBarHeight);
                mWindowManager.updateViewLayout(mFloatTv, layoutParams);
                return true;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }
}
