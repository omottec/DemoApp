package com.omottec.demoapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.omottec.demoapp.permission.CallPhoneFragment;
import com.omottec.demoapp.utils.TimeLogger;

/**
 * Created by qinbingbing on 3/31/16.
 */
public class DemoActivity extends SingleFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        TimeLogger.methodStart();
        super.onCreate(savedInstanceState);
        TimeLogger.methodEnd();
    }

    @Override
    protected void onResume() {
        TimeLogger.methodStart();
        super.onResume();
        TimeLogger.methodEnd();
        TimeLogger.dump();
    }

    @Override
    protected Fragment createFragment() {
        return new CallPhoneFragment();
    }
}