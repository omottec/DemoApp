package com.omottec.demoapp.activity;

import android.os.Bundle;
import android.os.StrictMode;
import android.os.SystemClock;
import androidx.fragment.app.FragmentActivity;

import com.omottec.demoapp.R;
import com.omottec.demoapp.app.MyApplication;
import com.omottec.demoapp.io.IoUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created by qinbingbing on 4/1/16.
 */
public class StrictModeActivity extends FragmentActivity {
    private byte[] bytes = new byte[10 * 1024 * 1024];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_text);
        MyApplication.sLeakActivities.add(this);
        IoUtils.write2ExternalStorage();
        TaskExecutor executor = new TaskExecutor();
        executor.executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public class TaskExecutor {
        public static final int SLOW_CALL_THRESHOLD= 500;
        public void executeTask(Runnable task) {
            long startTime = SystemClock.uptimeMillis();
            task.run();
            long cost = SystemClock.uptimeMillis() - startTime;
            if (cost > SLOW_CALL_THRESHOLD)
                StrictMode.noteSlowCall("slowCall cost=" + cost);
        }
    }
}
