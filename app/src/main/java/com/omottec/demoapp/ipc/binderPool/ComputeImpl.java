package com.omottec.demoapp.ipc.binderPool;

import android.os.RemoteException;

import com.omottec.demoapp.aidl.ICompute;

/**
 * Created by qinbingbing on 9/14/16.
 */
public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
