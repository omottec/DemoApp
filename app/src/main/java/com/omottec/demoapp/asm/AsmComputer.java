package com.omottec.demoapp.asm;

import android.util.Log;

public class AsmComputer {
    public static final String TAG = "AsmComputer";

    public void start() {
        //Log.d(TAG, "before start");
        Log.d(TAG, "start");
        //Log.d(TAG, "after start");
    }

    public int getStatus() {
        Log.d(TAG, "getStatus");
        //Log.d(TAG, "before return from getStatus");
        return 1;
    }

    public void playFilm(String name) {

    }

    private void work(String name, Thread thread) {

    }

    protected String foo(int[] arg1, String[] arg2) {
        return null;
    }
}
