package com.omottec.demoapp.ref;

import android.os.SystemClock;
import android.util.Log;

import com.omottec.demoapp.Tag;

/**
 * Created by qinbingbing on 14/06/2017.
 */

public class ReferentObject {
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        Log.d(Tag.REF, "finalize " + this);
    }
}
