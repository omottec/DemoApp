package com.omottec.demoapp.activity;

import android.support.v4.app.Fragment;

import com.omottec.demoapp.fragment.MobileInfoFragment;
import com.omottec.demoapp.push.SendPushFragment;
import com.omottec.demoapp.rxjava.RxJava2Fragment;
import com.omottec.demoapp.text.TextFragment;
import com.omottec.demoapp.view.recycler.PtrRecyclerViewFragment;
import com.omottec.demoapp.view.recycler.RecyclerViewFragment;

/**
 * Created by qinbingbing on 3/31/16.
 */
public class DemoActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new MobileInfoFragment();
    }
}
