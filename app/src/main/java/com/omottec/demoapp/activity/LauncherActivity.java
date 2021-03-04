package com.omottec.demoapp.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.widget.TextView;

import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 9/20/16.
 */
public class LauncherActivity extends FragmentActivity {
    private TextView mTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_text);
        mTv = findViewById(R.id.tv);
        mTv.setOnClickListener(v -> {
            Intent intent = new Intent(LauncherActivity.this, DialogActivity.class);
            startActivity(intent);
        });
    }
}
