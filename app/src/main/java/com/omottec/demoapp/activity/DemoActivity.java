package com.omottec.demoapp.activity;

import android.support.v4.app.Fragment;

import com.omottec.demoapp.fragment.AnimatorFragment;
import com.omottec.demoapp.fragment.CircleViewFragment;
import com.omottec.demoapp.fragment.InInterceptHorizontalSlideFragment;
import com.omottec.demoapp.fragment.IpFragment;
import com.omottec.demoapp.fragment.JniFragment;
import com.omottec.demoapp.fragment.LayoutAnimFragment;
import com.omottec.demoapp.fragment.OutInterceptHorizontalSlideFragment;
import com.omottec.demoapp.fragment.ShadowEntranceFragment;
import com.omottec.demoapp.fragment.SimpleFragment;
import com.omottec.demoapp.fragment.ViewFragment;

/**
 * Created by qinbingbing on 3/31/16.
 */
public class DemoActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new ViewFragment();
    }
}
