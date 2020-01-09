package com.omottec.demoapp.aspectj;

import android.util.Log;

public class People {
    static {
        Log.i(TestAspect.TAG, "execute people static initialization");
        int a = 10;
    }
}
