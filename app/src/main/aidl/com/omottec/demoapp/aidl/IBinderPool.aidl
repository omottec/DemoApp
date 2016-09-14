// IBinderPool.aidl
package com.omottec.demoapp.aidl;

// Declare any non-default types here with import statements

interface IBinderPool {
    IBinder queryBinder(int binderCode);
}
