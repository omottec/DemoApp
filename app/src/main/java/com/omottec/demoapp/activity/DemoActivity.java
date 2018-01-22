package com.omottec.demoapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.omottec.demoapp.compact.ContextFragment;
import com.omottec.demoapp.fragment.FastClickFragment;
import com.omottec.demoapp.fragment.FrescoFragment;
import com.omottec.demoapp.fragment.MemoryFragment;
import com.omottec.demoapp.fragment.ScaleTypeFragment;
import com.omottec.demoapp.fragment.MultiProcessFragment;
import com.omottec.demoapp.fragment.ShapeDrawableFragment;
import com.omottec.demoapp.fragment.StateFragment;
import com.omottec.demoapp.gson.GsonFragment;
import com.omottec.demoapp.storage.StorageFragment;
import com.omottec.demoapp.text.TextFragment;
import com.omottec.demoapp.text.TextUtilsFragment;
import com.omottec.demoapp.utils.TimeLogger;

import java.sql.Time;

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
        return new ContextFragment();
    }
}