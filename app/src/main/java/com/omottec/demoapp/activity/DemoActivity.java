package com.omottec.demoapp.activity;

import android.support.v4.app.Fragment;

import com.omottec.demoapp.fragment.FastClickFragment;
import com.omottec.demoapp.fragment.FrescoFragment;
import com.omottec.demoapp.fragment.ScaleTypeFragment;
import com.omottec.demoapp.fragment.MultiProcessFragment;
import com.omottec.demoapp.fragment.ShapeDrawableFragment;
import com.omottec.demoapp.fragment.StateFragment;
import com.omottec.demoapp.text.TextFragment;

/**
 * Created by qinbingbing on 3/31/16.
 */
public class DemoActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new TextFragment();
    }
}