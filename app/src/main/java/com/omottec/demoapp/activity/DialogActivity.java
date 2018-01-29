package com.omottec.demoapp.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.Logger;

/**
 * Created by qinbingbing on 9/20/16.
 */
public class DialogActivity extends FragmentActivity {
    public static final String TAG = "DialogActivity";
    private TextView mTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_text);
        mTv = findViewById(R.id.tv);
        mTv.setOnClickListener(v -> {
            finish();
            mTv.postDelayed(() -> {
                showDialog();
            }, 3000);
        });
    }

    private void showDialog() {
        Logger.d(TAG, "showDialog isFinishing:" + isFinishing());
        Dialog dialog = new Dialog(getApplicationContext());
        TextView tv = new TextView(this);
        tv.setText("DialogActivity");
        dialog.setContentView(tv);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
        dialog.show();
    }
}
