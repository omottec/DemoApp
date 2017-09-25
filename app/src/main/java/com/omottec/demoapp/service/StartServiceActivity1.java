package com.omottec.demoapp.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.Logger;

/**
 * Created by qinbingbing on 25/09/2017.
 */

public class StartServiceActivity1 extends FragmentActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_start_service1);
        findViewById(R.id.tv_start_service1).setOnClickListener(this);
        findViewById(R.id.tv_stop_service1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_start_service1:
                Logger.d(Tag.SERVICE, "StartServiceActivity1 start service");
                intent = new Intent(this, AppService.class);
                startService(intent);
                break;
            case R.id.tv_stop_service1:
                Logger.d(Tag.SERVICE, "StartServiceActivity1 stop service");
                intent = new Intent(this, AppService.class);
                stopService(intent);
                break;
        }
    }
}
