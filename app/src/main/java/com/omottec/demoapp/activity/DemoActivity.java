package com.omottec.demoapp.activity;

import android.support.v4.app.Fragment;

import com.omottec.demoapp.fragment.CircleViewFragment;
import com.omottec.demoapp.fragment.InInterceptHorizontalSlideFragment;
import com.omottec.demoapp.fragment.OutInterceptHorizontalSlideFragment;
import com.omottec.demoapp.fragment.SimpleFragment;

/**
 * Created by qinbingbing on 3/31/16.
 */
public class DemoActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {


        return new SimpleFragment();
    }
}
