package com.omottec.demoapp.ipc.binderPool;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;

import com.omottec.demoapp.Tag;

/**
 * Created by qinbingbing on 9/14/16.
 */
public class BinderPoolService extends Service {
    @Override
    public void onCreate() {
        Log.d(Tag.IPC_BINDER_POOL, "BinderPoolService.onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(Tag.IPC_BINDER_POOL, "BinderPoolService.onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(Tag.IPC_BINDER_POOL, "BinderPoolService.onBind");
        return new BinderPool.BinderPoolImpl();
    }

    @Override
    public void onDestroy() {
        Log.d(Tag.IPC_BINDER_POOL, "BinderPoolService.onDestroy");
    }
}
