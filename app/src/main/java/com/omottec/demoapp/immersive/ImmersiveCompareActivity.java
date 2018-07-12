package com.omottec.demoapp.immersive;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 12/07/2018.
 */

public class ImmersiveCompareActivity extends AppCompatActivity
        implements View.OnClickListener {

    private TextView mNoImmersiveTv;
    private TextView mImmersiveTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_immersive_compare);
        mNoImmersiveTv = (TextView) findViewById(R.id.tv_no_immersive);
        mImmersiveTv = (TextView) findViewById(R.id.tv_immersive);
        mNoImmersiveTv.setOnClickListener(this);
        mImmersiveTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_no_immersive:
                NoImmersiveActivity.show(this);
                break;
            case R.id.tv_immersive:
                ImmersiveActivity.show(this);
                break;
        }
    }
}
