package com.omottec.demoapp.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.Logger;

/**
 * Created by qinbingbing on 25/09/2017.
 */

public class UseServiceActivity extends FragmentActivity implements View.OnClickListener {
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Logger.d(Tag.SERVICE, "UseServiceActivity onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Logger.d(Tag.SERVICE, "UseServiceActivity onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_use_service);
        findViewById(R.id.tv_start_service).setOnClickListener(this);
        findViewById(R.id.tv_stop_service).setOnClickListener(this);
        findViewById(R.id.tv_start_service_activity).setOnClickListener(this);
        findViewById(R.id.tv_bind_service).setOnClickListener(this);
        findViewById(R.id.tv_unbind_service).setOnClickListener(this);
        findViewById(R.id.tv_bind_service_activity).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.tv_start_service:
                Logger.d(Tag.SERVICE,  "UseServiceActivity start service");
                intent = new Intent(this, AppService.class);
                startService(intent);
                break;
            case R.id.tv_stop_service:
                Logger.d(Tag.SERVICE,  "UseServiceActivity stop service");
                intent = new Intent(this, AppService.class);
                stopService(intent);
                break;
            case R.id.tv_start_service_activity:
                intent = new Intent(this, StartServiceActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_bind_service:
                Logger.d(Tag.SERVICE, "UseServiceActivity bind service");
                intent = new Intent(this, AppService.class);
                bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.tv_unbind_service:
                Logger.d(Tag.SERVICE, "UseServiceActivity unbind service");
                intent = new Intent(this, AppService.class);
                unbindService(mServiceConnection);
                break;
            case R.id.tv_bind_service_activity:
                intent = new Intent(this, BindServiceActivity.class);
                startActivity(intent);
                break;
        }
    }
}
