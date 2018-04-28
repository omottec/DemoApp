package com.omottec.demoapp.rxjava.scheduler;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action0;
import rx.internal.schedulers.NewThreadScheduler;
import rx.internal.schedulers.NewThreadWorker;
import rx.internal.util.RxThreadFactory;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by qinbingbing on 28/04/2018.
 */

public class ContextAwareScheduler extends Scheduler {
    public static final ContextAwareScheduler INSTANCE = new ContextAwareScheduler();

    final NewThreadWorker worker;

    private ContextAwareScheduler() {
        worker = new NewThreadWorker(new RxThreadFactory("ContextAwareScheduler"));
    }

    @Override
    public Worker createWorker() {
        return new ContextAwareWorker(worker);
    }

    static final class ContextAwareWorker extends Worker {
        final CompositeSubscription tracking;
        final NewThreadWorker worker;

        public ContextAwareWorker(NewThreadWorker worker) {
            this.worker = worker;
            this.tracking = new CompositeSubscription();
        }

        @Override
        public Subscription schedule(Action0 action) {
            return schedule(action, 0, null);
        }

        @Override
        public Subscription schedule(Action0 action, long delayTime, TimeUnit unit) {
            if (isUnsubscribed())
                return Subscriptions.unsubscribed();
            Object context = ContextManager.get();
            Action0 a = () -> {
                ContextManager.set(context);
                action.call();
            };
            return worker.scheduleActual(a, delayTime, unit, tracking);
        }

        @Override
        public void unsubscribe() {
            tracking.unsubscribe();
        }

        @Override
        public boolean isUnsubscribed() {
            return tracking.isUnsubscribed();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Worker w = INSTANCE.createWorker();
        CountDownLatch cdl = new CountDownLatch(1);

        ContextManager.set(1);
        w.schedule(() -> {
            System.out.println(Thread.currentThread());
            System.out.println(ContextManager.get());
        });

        ContextManager.set(2);
        w.schedule(() -> {
            System.out.println(Thread.currentThread());
            System.out.println(ContextManager.get());
            cdl.countDown();
        });

        cdl.await();

        ContextManager.set(3);

        Observable.timer(500, TimeUnit.MILLISECONDS, INSTANCE)
                .doOnNext(aLong -> {
                    System.out.println(Thread.currentThread());
                    System.out.println(ContextManager.get());
                }).toBlocking().first();

        w.unsubscribe();
    }
}
