package com.omottec.demoapp.concurrent;

import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolFragment extends Fragment implements View.OnClickListener {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    // We want at least 2 threads and at most 4 threads in the core pool,
    // preferring to have 1 less than the CPU count to avoid saturating
    // the CPU with background work
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 1 * 60 * 60;
    private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "Thread #" + mCount.getAndIncrement());
        }
    };
    private static final AtomicInteger TASK_COUNT = new AtomicInteger(1);
    private static final BlockingQueue<Runnable> POOL_WORK_QUEUE = new LinkedBlockingDeque<>(8);
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR;

    static {
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_SECONDS,
                TimeUnit.SECONDS,
                POOL_WORK_QUEUE,
                THREAD_FACTORY);
        THREAD_POOL_EXECUTOR.allowCoreThreadTimeOut(true);
        THREAD_POOL_EXECUTOR.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                Logger.i(Tag.THREAD_POOL);
            }
        });
    }

    private TextView mCountTv;
    private TextView mAddTaskTv;
    private TextView mAddCrashTaskTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_thread_pool, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mCountTv = view.findViewById(R.id.tv_count);
        mAddTaskTv = view.findViewById(R.id.tv_add_task);
        mAddCrashTaskTv = view.findViewById(R.id.tv_add_carsh_task);
        mCountTv.setOnClickListener(this);
        mAddTaskTv.setOnClickListener(this);
        mAddCrashTaskTv.setOnClickListener(this);

        Logger.i(Tag.THREAD_POOL, "CPU_COUNT:" + CPU_COUNT
                + ", CORE_POOL_SIZE:" + CORE_POOL_SIZE
                + ", MAX_POOL_SIZE:" + MAX_POOL_SIZE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_count:
                showCount();
                break;
            case R.id.tv_add_task:
                onClickAddTask();
                break;
            case R.id.tv_add_carsh_task:
                onClickAddCrashTask();
                break;
            default:
                break;
        }
    }

    private void onClickAddTask() {
        THREAD_POOL_EXECUTOR.execute(new NamedRunnable());
        showCount();
    }

    private void onClickAddCrashTask() {
        THREAD_POOL_EXECUTOR.submit(new CrashRunnable());
    }

    private void showCount() {
        int activeThreadCount = THREAD_POOL_EXECUTOR.getActiveCount();
        long taskCount = THREAD_POOL_EXECUTOR.getTaskCount();
        long completedTaskCount = THREAD_POOL_EXECUTOR.getCompletedTaskCount();
        int queueSize = POOL_WORK_QUEUE.size();
        String msg = new StringBuilder("activeThreadCount:" + activeThreadCount)
                .append(", taskCount:").append(taskCount)
                .append(", completedTaskCount:").append(completedTaskCount)
                .append(", queueSize:").append(queueSize)
                .toString();
        mCountTv.setText(msg);
        Logger.i(Tag.THREAD_POOL, msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        THREAD_POOL_EXECUTOR.shutdown();
    }

    private static class NamedRunnable implements Runnable {
        private static final AtomicInteger TASK_COUNT = new AtomicInteger(1);
        private int mTaskId;
        public NamedRunnable() {
            mTaskId = TASK_COUNT.getAndIncrement();
        }

        @Override
        public void run() {
            Logger.i(Tag.THREAD_POOL, "Task #" + mTaskId + " start execute by " + Thread.currentThread());
            SystemClock.sleep(2 * 60 * 60 * 1000);
            Logger.i(Tag.THREAD_POOL, "Task #" + mTaskId + " end execute by " + Thread.currentThread());
        }
    }

    private static class CrashRunnable implements Runnable {

        @Override
        public void run() {
            Logger.i(Tag.THREAD_POOL);
            throw new IllegalStateException("CrashRunnable IllegalState");
        }
    }
}
