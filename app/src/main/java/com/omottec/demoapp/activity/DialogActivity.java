package com.omottec.demoapp.activity;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.view.WindowManager;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.logger.Logger;

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
