package com.omottec.demoapp.activity;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.TextView;

import com.github.omottec.apt.api.BindView;
import com.github.omottec.apt.api.ButterKnife;
import com.omottec.demoapp.R;

public class AptActivity extends Activity {
    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_text);
        ButterKnife.inject(this);

        tv.setText("这就是ButterKnife的原理");
    }
}
