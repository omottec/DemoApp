package com.omottec.demoapp.activity;

import android.support.v4.app.Fragment;

import com.omottec.demoapp.fragment.NinePatchFragment;
import com.omottec.demoapp.ref.RefGcFragment;

/**
 * Created by qinbingbing on 3/31/16.
 */
public class DemoActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new NinePatchFragment();
    }
}
