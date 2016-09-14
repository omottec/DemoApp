package com.omottec.demoapp.ipc.binderPool;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.omottec.demoapp.Constants;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.aidl.IBinderPool;

import java.util.concurrent.CountDownLatch;

/**
 * Created by qinbingbing on 9/14/16.
 */
public class BinderPool {
    private static BinderPool sInstance;
    private Context mContext;
    private CountDownLatch mLatch;
    private IBinderPool mIBinderPool;
    private volatile boolean mForceQuit;
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mIBinderPool != null && mIBinderPool.asBinder().isBinderAlive())
                mIBinderPool.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mIBinderPool = null;
            if (mForceQuit) return;
            bindService();
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(Tag.IPC_BINDER_POOL, "onServiceDisconnected");
            mIBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                mIBinderPool.asBinder().linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            if (mLatch != null) mLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {


        }
    };

    private BinderPool(Context context) {
        mContext = context;
        bindService();
    }

    public static BinderPool getInstance(Context context) {
        if (sInstance == null) {
            synchronized (BinderPool.class) {
                if (sInstance == null)
                    sInstance = new BinderPool(context);
            }
        }
        return sInstance;
    }

    public IBinder queryBinder(int binderCode) {
        if (mIBinderPool == null) return null;
        try {
            return mIBinderPool.queryBinder(binderCode);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void bindService() {
        mLatch = new CountDownLatch(1);
        Intent intent = new Intent(mContext, BinderPoolService.class);
        mContext.bindService(intent, mConnection, Service.BIND_AUTO_CREATE);
        try {
            mLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        mForceQuit = true;
        unbindService();
    }

    private void unbindService() {
        Intent intent = new Intent(mContext, BinderPoolService.class);
        mContext.bindService(intent, mConnection, Service.BIND_AUTO_CREATE);
    }

    public static class BinderPoolImpl extends IBinderPool.Stub {

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            switch (binderCode) {
                case Constants.BINDER_CODE_COMPUTE:
                    return new ComputeImpl();
                case Constants.BINDER_CODE_SECURITY_CENTER:
                    return new SecurityCenterImpl();
                default:
                    throw new IllegalArgumentException("no matched binder for binderCode:" + binderCode);
            }
        }
    }
}
