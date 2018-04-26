package com.omottec.demoapp.rxjava.serializedAccess;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.Objects;
import java.util.Queue;
import java.util.function.Consumer;

import rx.internal.util.unsafe.MpscLinkedQueue;

/**
 * Created by qinbingbing on 26/04/2018.
 */

public class ValueEmitterLoop<T> {
    Queue<T> queue = new MpscLinkedQueue<>();
    boolean emitting;
    Consumer<? super T> consumer;

    @TargetApi(Build.VERSION_CODES.N)
    public void emit(T value) {
        Objects.requireNonNull(value);
        queue.offer(value);
        synchronized (this) {
            if (emitting) {
                return;
            }
            emitting = true;
        }
        for (;;) {
            T v = queue.poll();
            if (v != null) {
                consumer.accept(v);
            } else {
                synchronized (this) {
                    if (queue.isEmpty()) {
                        emitting = false;
                        return;
                    }
                }
            }
        }
    }
}
