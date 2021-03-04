package com.omottec.demoapp.rxjava.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action0;
import rx.internal.schedulers.ScheduledAction;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by qinbingbing on 02/05/2018.
 */

public class CustomWorker extends Scheduler.Worker {
    static final ScheduledExecutorService genericScheduler;
    static {
        genericScheduler = Executors.newScheduledThreadPool(1, r -> {
            Thread t = new Thread(r, "GenericScheduler");
            t.setDaemon(true);
            return t;
        });
    }

    final ExecutorService exec;
    final CompositeSubscription tracking;
    final boolean shutdown;

    public CustomWorker() {
        exec = Executors.newSingleThreadExecutor();
        tracking = new CompositeSubscription();
        shutdown = true;
    }

    public CustomWorker(ExecutorService exec) {
        this.exec = exec;
        tracking = new CompositeSubscription();
        shutdown = false;
    }

    @Override
    public Subscription schedule(Action0 action) {
        return schedule(action, 0, null);
    }

    @Override
    public Subscription schedule(Action0 action, long delayTime, TimeUnit unit) {
        if (isUnsubscribed())
            return Subscriptions.unsubscribed();
        ScheduledAction sa = new ScheduledAction(action);

        tracking.add(sa);
        sa.add(Subscriptions.create(() -> {
            tracking.remove(sa);
        }));
        Future<?> f = null;
        if (delayTime <= 0) {
            f = exec.submit(sa);
        } else if (exec instanceof ScheduledExecutorService) {
            f = ((ScheduledExecutorService)exec).schedule(sa, delayTime, unit);
        } else {
            genericScheduler.schedule(() -> {
                Future<?> g = exec.submit(sa);
                sa.add(Subscriptions.create(() -> g.cancel(false)));
            }, delayTime, unit);
        }
        Future<?> finalF = f;
        sa.add(Subscriptions.create(() -> {
            finalF.cancel(false);
        }));
        return null;
    }

    @Override
    public void unsubscribe() {
        if (shutdown)
            exec.shutdownNow();
        tracking.unsubscribe();
    }

    @Override
    public boolean isUnsubscribed() {
        return tracking.isUnsubscribed();
    }
}
