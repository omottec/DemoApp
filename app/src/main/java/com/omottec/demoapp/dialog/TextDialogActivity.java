package com.omottec.demoapp.dialog;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 13/12/2017.
 */

public class TextDialogActivity extends FragmentActivity implements View.OnClickListener {
    private TextView mTv;
    private TextDialogFragment mDialogFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_text);
        mTv = findViewById(R.id.tv);
        mTv.setText("TextDialogActivity");
        mTv.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDialogFragment = TextDialogFragment.newInstance("TextDialogFragment");
        mDialogFragment.show(getSupportFragmentManager(), null);
    }

    @Override
    public void onClick(View v) {
        mDialogFragment.dismiss();
    }
}
