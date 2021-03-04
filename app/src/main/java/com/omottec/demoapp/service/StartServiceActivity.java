package com.omottec.demoapp.service;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.view.View;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.logger.Logger;

/**
 * Created by qinbingbing on 25/09/2017.
 */

public class StartServiceActivity extends FragmentActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_start_service);
        findViewById(R.id.tv_start_service).setOnClickListener(this);
        findViewById(R.id.tv_stop_service).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_start_service:
                Logger.d(Tag.SERVICE, "StartServiceActivity start service");
                intent = new Intent(this, AppService.class);
                startService(intent);
                break;
            case R.id.tv_stop_service:
                Logger.d(Tag.SERVICE, "StartServiceActivity stop service");
                intent = new Intent(this, AppService.class);
                stopService(intent);
                break;
        }
    }
}
