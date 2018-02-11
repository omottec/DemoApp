package com.omottec.demoapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.omottec.demoapp.fresco.BatchLoadFragment;
import com.omottec.demoapp.fresco.FrescoFragment;
import com.omottec.demoapp.utils.TimeLogger;
import com.omottec.demoapp.view.recycler.PtrPicRecyclerFragment;

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
        return new BatchLoadFragment();
    }
}