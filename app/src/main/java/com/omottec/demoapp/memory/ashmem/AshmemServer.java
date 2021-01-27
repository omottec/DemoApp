package com.omottec.demoapp.memory.ashmem;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.omottec.demoapp.Tag;
import com.omottec.logger.Logger;

public class AshmemServer extends Service {
    private AshmemService mAshmemService;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Logger.i(Tag.ASHMEM, "mAshmemService:" + mAshmemService);
        return mAshmemService;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Logger.i(Tag.ASHMEM);
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        Logger.i(Tag.ASHMEM);
        mAshmemService = new AshmemService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.i(Tag.ASHMEM);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Logger.i(Tag.ASHMEM);
    }
}
