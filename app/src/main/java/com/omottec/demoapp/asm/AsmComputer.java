package com.omottec.demoapp.asm;

import android.util.Log;

public class AsmComputer {
    public static final String TAG = "AsmComputer";

    public void start() {
        Log.d(TAG, "start");
    }

    public int getStatus() {
        Log.d(TAG, "getStatus");
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
