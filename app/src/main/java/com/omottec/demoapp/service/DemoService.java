package com.omottec.demoapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;

import com.omottec.demoapp.Tag;

/**
 * Created by qinbingbing on 4/26/16.
 */
public class DemoService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(Tag.APP_PROCESS, "DemoService.onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(Tag.APP_PROCESS, "DemoService.onDestroy");
        super.onDestroy();
    }
}
