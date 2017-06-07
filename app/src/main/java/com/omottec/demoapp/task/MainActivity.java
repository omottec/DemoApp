package com.omottec.demoapp.task;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.activity.DemoActivity;
import com.omottec.demoapp.app.MyApplication;
import com.omottec.demoapp.view.frame.TabPagerActivity;

/**
 * Created by qinbingbing on 3/23/16.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private static int count;
    private static final int ID = count++;
    private long mLastBackPressTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two_text);
        TextView tv = (TextView) findViewById(R.id.tv);
        TextView tv1 = (TextView) findViewById(R.id.tv1);
        tv.setBackgroundColor(Color.GREEN);
        tv.setText("MainActivity" + ID);
        tv.setOnClickListener(this);
        tv1.setOnClickListener(this);
        Log.d(Tag.TASK, "taskId:" + getTaskId() + "|" + this + "|onCreate|" + ID);
    }

    private void startSelf() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(Tag.TASK, "taskId:" + getTaskId() + "|" + this + "|onNewIntent");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(Tag.TASK, "taskId:" + getTaskId() + "|" + this + "|onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Tag.TASK, "taskId:" + getTaskId() + "|" + this + "|onStart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(Tag.TASK, "taskId:" + getTaskId() + "|" + this + "|onRestoreInstanceState");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Tag.TASK, "taskId:" + getTaskId() + "|" + this + "|onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Tag.TASK, "taskId:" + getTaskId() + "|" + this + "|onPause");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(Tag.TASK, "taskId:" + getTaskId() + "|" + this + "|onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Tag.TASK, "taskId:" + getTaskId() + "|" + this + "|onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Tag.TASK, "taskId:" + getTaskId() + "|" + this + "|onDestroy");
    }

    private void startWelcomeActivity() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv:
//                startExternalActivity();
//                startWelcomeActivity();
//                startTabPagerActivity();
                startDemoActivity();
                break;
            case R.id.tv1:
                startSelf();
                break;
        }
    }

    private void startExternalActivity() {
        Intent intent = new Intent("com.omottec.demoapp1.action.MAIN");
//        Intent intent = new Intent("com.omottec.demoapp1.action.WELCOM");

//        Intent intent = new Intent();
//        ComponentName componentName = new ComponentName("com.omottec.demoapp1", "com.omottec.demoapp1.task.MainActivity");
//        intent.setComponent(componentName);
        startActivity(intent);
    }

    private void startTabPagerActivity() {
        Intent intent = new Intent(this, TabPagerActivity.class);
        startActivity(intent);
    }

    private void startDemoActivity() {
        Intent intent = new Intent(this, DemoActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (elapsedRealtime - mLastBackPressTime < 2000) {
            super.onBackPressed();
        } else {
            Toast.makeText(MyApplication.getContext(), "再按一次退出~", Toast.LENGTH_SHORT).show();
            mLastBackPressTime = elapsedRealtime;
        }
    }
}
