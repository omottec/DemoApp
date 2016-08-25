package com.omottec.demoapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 4/30/16.
 */
public class HandlerActivity extends FragmentActivity {
    public static final String TAG = "s";
    private Handler mUiHandler;
    private HandlerThread mHandlerThread = new HandlerThread(TAG);
    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_text);
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                logIsUiThread("mHandler");
            }
        };
        mUiHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                logIsUiThread("mUiHandler");
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.sendEmptyMessage(0);
        mUiHandler.sendEmptyMessage(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void logIsUiThread(String msg) {
        Log.d(TAG, msg + " UiThread:" + (Looper.myLooper() == Looper.getMainLooper()));
    }
}
