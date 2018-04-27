package com.omottec.demoapp.rxjava.serializedAccess;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import rx.internal.util.unsafe.MpscLinkedQueue;

/**
 * Created by qinbingbing on 27/04/2018.
 */

public class ValueQueueDrain<T> {
    final Queue<T> queue = new MpscLinkedQueue<>();
    final AtomicInteger wip = new AtomicInteger();
    Consumer consumer;

    @TargetApi(Build.VERSION_CODES.N)
    public void drain(T value) {
        queue.offer(Objects.requireNonNull(value));
        if (wip.getAndIncrement() == 0) {
            do {
                T v = queue.poll();
                consumer.accept(v);
            } while (wip.decrementAndGet() != 0);
        }
    }
}
