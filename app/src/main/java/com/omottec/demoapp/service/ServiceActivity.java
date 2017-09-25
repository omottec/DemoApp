package com.omottec.demoapp.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 25/09/2017.
 */

public class ServiceActivity extends FragmentActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_service);
        findViewById(R.id.tv_start_service).setOnClickListener(this);
        findViewById(R.id.tv_start_service1).setOnClickListener(this);
        findViewById(R.id.tv_bind_service).setOnClickListener(this);
        findViewById(R.id.tv_bind_service1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.tv_start_service:
                intent = new Intent(this, StartServiceActivity.class);
                break;
            case R.id.tv_start_service1:
                intent = new Intent(this, StartServiceActivity1.class);
                break;
            case R.id.tv_bind_service:
                intent = new Intent(this, BindServiceActivity.class);
                break;
            case R.id.tv_bind_service1:
                intent = new Intent(this, BindServiceActivity1.class);
                break;
        }
        startActivity(intent);
    }
}
