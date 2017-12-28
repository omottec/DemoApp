package com.omottec.demoapp.task;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.Logger;

//import com.bumptech.glide.Glide;

/**
 * Created by qinbingbing on 3/23/16.
 */
public class StandardActivity extends FragmentActivity implements View.OnClickListener {
    private static int count;
    private final int ID = count++;
    private TextView mTv;
    private TextView mTv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_launch_mode_two_text);
        mTv = (TextView) findViewById(R.id.tv);
        mTv1 = (TextView) findViewById(R.id.tv1);
        mTv.setBackgroundColor(Color.BLUE);
        mTv.setText("StandardActivity" + ID);
        Log.d(Tag.TASK, "taskId:" + getTaskId() + "|" + this + "|onCreate|" + ID);
        mTv.setOnClickListener(this);
        mTv1.setOnClickListener(this);

    }

    private void startSelf() {
        Intent intent = new Intent(this, StandardActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(Tag.TASK, "taskId:" + getTaskId() + "|" + this + "onNewIntent");
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
        TaskUtils.logTaskActivity();
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

    private void startOtherActivity() {
        Intent intent = new Intent(StandardActivity.this, SingleInstanceActivity.class);
        startActivity(intent);
//        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv:
                startSelf();
                break;
            case R.id.tv1:
                startOtherActivity();
                break;
        }
    }
}
