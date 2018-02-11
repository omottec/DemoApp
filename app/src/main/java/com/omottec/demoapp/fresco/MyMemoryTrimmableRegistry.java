package com.omottec.demoapp.fresco;

import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.MemoryTrimmableRegistry;

/**
 * Created by qinbingbing on 11/02/2018.
 */

public class MyMemoryTrimmableRegistry implements MemoryTrimmableRegistry {
    private static MyMemoryTrimmableRegistry sInstance = null;

    private MyMemoryTrimmableRegistry() {
    }

    public static synchronized MyMemoryTrimmableRegistry getInstance() {
        if (sInstance == null) {
            sInstance = new MyMemoryTrimmableRegistry();
        }
        return sInstance;
    }

    @Override
    public void registerMemoryTrimmable(MemoryTrimmable trimmable) {

    }

    @Override
    public void unregisterMemoryTrimmable(MemoryTrimmable trimmable) {

    }
}
