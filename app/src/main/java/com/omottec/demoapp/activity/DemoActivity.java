package com.omottec.demoapp.activity;

import android.support.v4.app.Fragment;

import com.omottec.demoapp.rxjava.RxJavaFragment;
import com.omottec.demoapp.view.recycler.PtrRecyclerViewFragment;

/**
 * Created by qinbingbing on 3/31/16.
 */
public class DemoActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new PtrRecyclerViewFragment();
    }
}
