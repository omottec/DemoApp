package com.omottec.demoapp.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.omottec.demoapp.R;

public class SubProcActivity extends Activity implements View.OnClickListener {
    public static final String TAG = "SubProcActivity";
    private TextView mQueryProviderTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, this + " onCreate");
        setContentView(R.layout.a_sub_proc);
        mQueryProviderTv = findViewById(R.id.tv_query_provider);
        mQueryProviderTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_query_provider:
                queryProvider();
                break;
        }
    }

    private void queryProvider() {
        Log.i(TAG, this + " queryProvider");
        ContentResolver contentResolver = getContentResolver();
        contentResolver.query(Uri.parse("content://" + getPackageName() + ".DemoProvider"),
                null, null, null, null);
    }
}
