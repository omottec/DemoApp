package com.omottec.demoapp.activity;

import android.support.v4.app.Fragment;

import com.omottec.demoapp.fragment.InInterceptHorizontalSlideFragment;
import com.omottec.demoapp.fragment.OutInterceptHorizontalSlideFragment;

/**
 * Created by qinbingbing on 3/31/16.
 */
public class DemoActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {


        return new InInterceptHorizontalSlideFragment();
    }
}
