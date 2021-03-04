package com.omottec.demoapp.rxjava.serializedAccess;

import android.annotation.TargetApi;
import android.os.Build;
import androidx.annotation.RequiresApi;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import rx.internal.util.unsafe.MpscLinkedQueue;

/**
 * Created by qinbingbing on 27/04/2018.
 */

public class ValueQueueDrainFastpath<T> {
    final Queue<T> queue = new MpscLinkedQueue<>();
    final AtomicInteger wip = new AtomicInteger();
    Consumer consumer;

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void drain(T value) {
        Objects.requireNonNull(value);
        if (wip.compareAndSet(0, 1)) {
            consumer.accept(value);
            if (wip.decrementAndGet() == 0) {
                return;
            }
        } else {
            queue.offer(value);
            if (wip.getAndIncrement() != 0) {
                return;
            }
        }
        do {
            T v = queue.poll();
            consumer.accept(v);
        } while (wip.decrementAndGet() != 0);
    }
}
