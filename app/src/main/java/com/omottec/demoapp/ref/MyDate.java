package com.omottec.demoapp.ref;

import android.util.Log;

import com.omottec.demoapp.Tag;

import java.util.Date;

/**
 * Created by qinbingbing on 14/06/2017.
 */

public class MyDate extends Date {
    public MyDate() {
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.d(Tag.REF, this + " is gc");
    }

    @Override
    public String toString() {
        return "MyDate [Time: " + this.getTime() + ", Hash:" + hashCode() + "]";
    }
}