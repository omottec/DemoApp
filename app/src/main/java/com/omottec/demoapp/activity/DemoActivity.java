package com.omottec.demoapp.activity;

import android.support.v4.app.Fragment;

import com.omottec.demoapp.fragment.SimpleFragment;
import com.omottec.demoapp.view.lv.ListViewFragment;
import com.omottec.demoapp.view.ptr.Pull2RefreshFragment;
import com.omottec.demoapp.view.recycler.PtrRecyclerViewFragment;
import com.omottec.demoapp.view.recycler.RecyclerViewFragment;

/**
 * Created by qinbingbing on 3/31/16.
 */
public class DemoActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new PtrRecyclerViewFragment();
    }
}
