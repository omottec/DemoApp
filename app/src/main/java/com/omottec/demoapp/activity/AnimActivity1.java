package com.omottec.demoapp.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.utils.UiUtils;

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
        mTv.postDelayed(new Runnable() {
            @Override
            public void run() {
                UiUtils.logRootView(getWindow());
            }
        }, 1000);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.down_2_up, R.anim.up_2_down);
    }
}
