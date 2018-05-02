package com.omottec.demoapp.rxjava.subscribeOn;

import com.omottec.demoapp.rxjava.IDisposable;
import com.omottec.demoapp.rxjava.IObservable;
import com.omottec.demoapp.rxjava.IObserver;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by qinbingbing on 02/05/2018.
 */

public class SubscribeOnTest {
    public static void main(String[] args) {
        IObservable<Object> sleeper = o -> {
            try {
                Thread.sleep(1000);
                o.onCompleted();
            } catch (InterruptedException e) {
                o.onError(e);
            }
            return null;
        };

        ExecutorService exec = Executors.newSingleThreadExecutor();
        IObservable<Object> subscribeOn = o -> {
            Future<?> f = exec.submit(() -> sleeper.subscribe(o));
            return () -> f.cancel(true);
        };

        ExecutorService exec2 = Executors.newSingleThreadExecutor();
        IObservable<Object> subscribeOn2 = o -> {
            Future<?> f2 = exec2.submit(() -> subscribeOn.subscribe(o));
            return () -> f2.cancel(true);
        };
    }
}
