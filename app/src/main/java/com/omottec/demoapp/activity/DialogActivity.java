package com.omottec.demoapp.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 9/20/16.
 */
public class DialogActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_text);
        showDialog();
    }

    private void showDialog() {
        Dialog dialog = new Dialog(getApplicationContext());
        TextView tv = new TextView(this);
        tv.setText("DialogActivity");
        dialog.setContentView(tv);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
        dialog.show();
    }
}
