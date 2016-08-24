package com.omottec.demoapp.activity;

import android.support.v4.app.Fragment;

import com.omottec.demoapp.fragment.BuildConfigFragment;
import com.omottec.demoapp.fragment.MemoryFragment;
import com.omottec.demoapp.fragment.MultiProcessFragment;
import com.omottec.demoapp.fragment.ScaleTypeFragment;
import com.omottec.demoapp.fragment.SimpleFragment;
import com.omottec.demoapp.fragment.SmartBarFragment;

/**
 * Created by qinbingbing on 3/31/16.
 */
public class DemoActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {


        return new ScaleTypeFragment();
    }
}
