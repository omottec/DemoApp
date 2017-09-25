package com.omottec.demoapp.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.Logger;

/**
 * Created by qinbingbing on 25/09/2017.
 */

public class BindServiceActivity extends FragmentActivity implements View.OnClickListener {
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Logger.d(Tag.SERVICE, "BindServiceActivity onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Logger.d(Tag.SERVICE, "BindServiceActivity onServiceDisconnected");
        }
    };

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, AppService.class);
        switch (v.getId()) {
            case R.id.tv_bind_service:
                Logger.d(Tag.SERVICE, "BindServiceActivity bind service");
                bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.tv_unbind_service:
                Logger.d(Tag.SERVICE, "BindServiceActivity unbind service");
                unbindService(mServiceConnection);
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_bind_service);
        findViewById(R.id.tv_bind_service).setOnClickListener(this);
        findViewById(R.id.tv_unbind_service).setOnClickListener(this);
    }
}
