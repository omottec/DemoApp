package com.omottec.demoapp.task;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;

/**
 * Created by qinbingbing on 3/23/16.
 */
public class WelcomeActivity extends FragmentActivity implements View.OnClickListener {
    private static int count;
    private final int ID = count++;
    private Handler mHandler = new Handler();
    private TextView mTv;
    private TextView mTv1;
    private ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two_text);
        mTv = (TextView) findViewById(R.id.tv);
        mTv1 = (TextView) findViewById(R.id.tv1);
        mIv = (ImageView) findViewById(R.id.iv);
        mTv.setBackgroundColor(Color.BLUE);
        mTv.setText("WelcomeActivity" + ID);
        Log.d(Tag.TASK, "taskId:" + getTaskId() + "|" + this + "|onCreate|" + ID);
        mHandler = new Handler();
        /*mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }, 200);*/
        mTv.setOnClickListener(this);
        mTv1.setOnClickListener(this);

    }

    private void startSelf() {
        Intent intent = new Intent(this, WelcomeActivity.class);
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
        Glide.with(WelcomeActivity.this).load("http://goo.gl/gEgYUd").into(mIv);
        /*mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Glide.with(WelcomeActivity.this).load("http://goo.gl/gEgYUd").into(mIv);
            }
        }, 2000);*/
    }

    private void startMainActivity() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv:
                startMainActivity();
                break;
            case R.id.tv1:
                startSelf();
                break;
        }
    }
}
