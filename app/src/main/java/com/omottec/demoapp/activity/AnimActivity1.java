package com.omottec.demoapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 9/21/16.
 */
public class AnimActivity1 extends FragmentActivity {
    TextView mTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_text);
        mTv = (TextView) findViewById(R.id.tv);
        mTv.setText("AnimActivity1");
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.down_2_up, R.anim.up_2_down);
    }
}
