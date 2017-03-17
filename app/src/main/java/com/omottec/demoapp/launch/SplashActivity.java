package com.omottec.demoapp.launch;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.activity.BaseActivity;
import com.omottec.demoapp.app.MyApplication;
import com.omottec.demoapp.task.MainActivity;

/**
 * Created by qinbingbing on 17/03/2017.
 */

public class SplashActivity extends BaseActivity {
    private View mContentView;
    private ImageView mSplashIv;
    private boolean mJump2Main = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mJump2Main) {
            jump2MainActivity();
        } else {
            mSplashIv.setImageResource(R.drawable.splash);
            MyApplication.getUiHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    jump2MainActivity();
                }
            }, 2 * 1000);
        }
    }

    private void jump2MainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected View createContentView() {
        mContentView = View.inflate(this, R.layout.a_splash, null);
        mSplashIv = (ImageView) mContentView.findViewById(R.id.iv_splash);
        return mContentView;
    }
}
