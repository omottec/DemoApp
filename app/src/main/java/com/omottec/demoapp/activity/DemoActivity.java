package com.omottec.demoapp.activity;

import android.support.v4.app.Fragment;

import com.omottec.demoapp.fragment.DynamicProxyFragment;
import com.omottec.demoapp.fragment.FrescoFragment;
import com.omottec.demoapp.fragment.NinePatchFragment;
import com.omottec.demoapp.fragment.WebViewFragment;
import com.omottec.demoapp.gson.GsonFragment;
import com.omottec.demoapp.ref.RefGcFragment;
import com.omottec.demoapp.storage.StorageFragment;
import com.omottec.demoapp.text.TextFragment;
import com.omottec.demoapp.view.frame.MajorDetailFragment;
import com.omottec.demoapp.view.recycler.PtrPicRecyclerFragment;
import com.omottec.demoapp.view.recycler.PtrRecyclerViewFragment;
import com.omottec.demoapp.view.statuslayout.MultiPartStatusFragment;

/**
 * Created by qinbingbing on 3/31/16.
 */
public class DemoActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new StorageFragment();
    }
}