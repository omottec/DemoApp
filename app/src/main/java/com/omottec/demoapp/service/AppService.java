package com.omottec.demoapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.Logger;

/**
 * Created by qinbingbing on 25/09/2017.
 */

public class AppService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Logger.d(Tag.SERVICE, "AppService.onBind");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Logger.d(Tag.SERVICE, "AppService.onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Logger.d(Tag.SERVICE, "AppService.onStart");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d(Tag.SERVICE, "AppService.onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        Logger.d(Tag.SERVICE, "AppService.onCreate");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Logger.d(Tag.SERVICE, "AppService.onDestroy");
        super.onDestroy();
    }
}
