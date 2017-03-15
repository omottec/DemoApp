package com.omottec.demoapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.omottec.demoapp.R;

/**
 * Created by omottec on 14/03/2017.
 */

public class NetAccessActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = "NetAccessActivity";
    private Handler mHandler;
    private TextView mLoadingTv;
    private TextView mNormalTv;
    private TextView mEmptyTv;
    private TextView mErrorTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mToolbar.setLogo(R.drawable.ic_launcher);
//        mToolbar.setTitle(R.string.title);
//        mToolbar.setSubtitle(R.string.sub_title);
//        setSupportActionBar(mToolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "navigationOnClick");
//            }
//        });
        mHandler = new Handler();
        mLoadingTv = (TextView) findViewById(R.id.tv_loading);
        mNormalTv = (TextView) findViewById(R.id.tv_normal);
        mEmptyTv = (TextView) findViewById(R.id.tv_empty);
        mErrorTv = (TextView) findViewById(R.id.tv_error);
        mLoadingTv.setOnClickListener(this);
        mNormalTv.setOnClickListener(this);
        mEmptyTv.setOnClickListener(this);
        mErrorTv.setOnClickListener(this);
        setState(STATE_LOADING);
        setOnClickEmptyOrErrorListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setState(STATE_LOADING);
            }
        });
    }

    @Override
    protected View createContentView() {
        return View.inflate(this, R.layout.a_net_access, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setState(STATE_NORMAL);
            }
        }, 10 * 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_loading:
                setState(STATE_LOADING);
                break;
            case R.id.tv_normal:
                setState(STATE_NORMAL);
                break;
            case R.id.tv_empty:
                setState(STATE_EMPTY);
                break;
            case R.id.tv_error:
                setState(STATE_ERROR);
                break;
        }
    }
}
