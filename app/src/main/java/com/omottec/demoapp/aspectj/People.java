package com.omottec.demoapp.aspectj;

import android.util.Log;
import com.omottec.demoapp.Tag;

public class People {
    static {
        Log.i(Tag.ASPECTJ, "execute people static initialization");
        int a = 10;
    }
}
