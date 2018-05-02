package com.omottec.demoapp.rxjava.scheduler;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action0;
import rx.internal.schedulers.ScheduledAction;
import rx.observers.TestSubscriber;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by qinbingbing on 02/05/2018.
 */

public class ExecutorScheduler extends Scheduler {
    final Executor exec;
    final AtomicInteger wip = new AtomicInteger();
    final Queue<ScheduledAction> queue = new ConcurrentLinkedQueue<>();
    final CompositeSubscription tracking = new CompositeSubscription();

    public ExecutorScheduler(Executor exec) {
        this.exec = exec;
    }


    @Override
    public Worker createWorker() {
        return new ExecutorWorker();
    }


    final class ExecutorWorker extends Worker implements Runnable {

        @Override
        public void run() {
            do {
                if (isUnsubscribed()) {
                    queue.clear();
                    return;
                }
                ScheduledAction sa = queue.poll();
                if (sa != null && !sa.isUnsubscribed())
                    sa.run();
            } while (wip.decrementAndGet() > 0);
        }

        @Override
        public Subscription schedule(Action0 action) {
            if (isUnsubscribed())
                return Subscriptions.unsubscribed();
            ScheduledAction sa = new ScheduledAction(action);
            tracking.add(sa);
            sa.add(Subscriptions.create(() -> {
                tracking.remove(sa);
            }));

            queue.offer(sa);
            sa.add(Subscriptions.create(() -> {
                queue.remove(sa);
            }));

            if (wip.getAndIncrement() == 0)
                exec.execute(sa);
            return sa;
        }

        @Override
        public Subscription schedule(Action0 action, long delayTime, TimeUnit unit) {
            if (delayTime <= 0)
                return schedule(action);
            if (isUnsubscribed())
                return Subscriptions.unsubscribed();
            ScheduledAction sa = new ScheduledAction(action);
            tracking.add(sa);
            sa.add(Subscriptions.create(() -> {
                tracking.remove(sa);
            }));

            ScheduledExecutorService schedex;
            if (exec instanceof ScheduledExecutorService) {
                schedex = (ScheduledExecutorService) exec;
            } else {
                schedex = CustomWorker.genericScheduler;
            }

            Future<?> f = schedex.schedule(() -> {
                queue.offer(sa);
                sa.add(Subscriptions.create(() -> {
                    queue.remove(sa);
                }));
                if (wip.getAndIncrement() == 0) {
                    exec.execute(this);
                }
            }, delayTime, unit);
            sa.add(Subscriptions.create(() -> {
                f.cancel(false);
            }));
            return sa;
        }

        @Override
        public void unsubscribe() {
            queue.clear();
            tracking.unsubscribe();
        }

        @Override
        public boolean isUnsubscribed() {
            return tracking.isUnsubscribed();
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(3);
        try {
            Scheduler s = new ExecutorScheduler(exec);

            Observable<Integer> source = Observable.just(1)
                    .delay(500, TimeUnit.MILLISECONDS, s)
                    .doOnNext(v -> {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        System.out.println(Thread.currentThread());
                    });
            TestSubscriber<Integer> ts1 = new TestSubscriber<>();
            TestSubscriber<Integer> ts2 = new TestSubscriber<>();
            TestSubscriber<Integer> ts3 = new TestSubscriber<>();

            source.subscribe(ts1);
            source.subscribe(ts2);
            source.subscribe(ts3);

            ts1.awaitTerminalEvent();
            ts1.assertNoErrors();
            ts1.assertValue(1);

            ts2.awaitTerminalEvent();
            ts2.assertNoErrors();
            ts2.assertValue(1);

            ts3.awaitTerminalEvent();
            ts3.assertNoErrors();
            ts3.assertValue(1);
        } finally {
            exec.shutdown();
        }
    }
}
