package com.omottec.demoapp.rxjava.serializedAccess;

/**
 * Created by qinbingbing on 26/04/2018.
 */

public class EmitterLoopSerializer {
    boolean emitting;
    boolean missed;
    public void emit() {
        synchronized (this) {
            if (emitting) {
                missed = true;
                return;
            }
            emitting = true;
        }
        for (;;) {
            // do all emission work
            synchronized (this) {
                if (!missed) {
                    emitting = false;
                    return;
                }
                missed = false;
            }
        }
    }
}
