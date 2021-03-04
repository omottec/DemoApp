package com.omottec.demoapp.rxjava.serializedAccess;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by qinbingbing on 26/04/2018.
 */

public class ValueListEmitterLoop<T> {
    List<T> queue;
    boolean emitting;
    Consumer<? super T> consumer;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void emit(T value) {
        synchronized (this) {
            if (emitting) {
                List<T> q = queue;
                if (q == null) {
                    q = new ArrayList<>();
                    queue = q;
                }
                q.add(value);
                return;
            }
            emitting = true;
        }
        boolean skipFinal = false;
        try {
            consumer.accept(value);
            for(;;) {
                List<T> q;
                synchronized (this) {
                    q = queue;
                    if (q == null) {
                        emitting = false;
                        skipFinal = true;
                        return;
                    }
                    queue = null;
                }
                q.forEach(consumer);
            }
        } finally {
            if (!skipFinal) {
                synchronized (this) {
                    emitting = false;
                }
            }
        }

    }
}
