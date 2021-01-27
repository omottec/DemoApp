package com.omottec.demoapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.omottec.demoapp.R;
import com.omottec.logger.Logger;

/**
 * Created by qinbingbing on 4/30/16.
 */
public class HandlerActivity extends FragmentActivity {
    public static final String TAG = "HandlerActivity";
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
                Logger.i(TAG, "mHandler");
            }
        };
        mUiHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Logger.i(TAG, "mUiHandler");
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
//        mHandler.sendEmptyMessage(0);
//        mUiHandler.sendEmptyMessage(0);

        Logger.i(TAG, "aaa");
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                Logger.i(TAG, "bbb");
            }
        });
        Logger.i(TAG, "ccc");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
