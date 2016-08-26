package com.omottec.demoapp.activity;

import android.support.v4.app.Fragment;

import com.omottec.demoapp.fragment.PropertyAnimMoveViewFragment;
import com.omottec.demoapp.fragment.ScrollerImageViewFragment;
import com.omottec.demoapp.fragment.ScrollerTextViewFragment;
import com.omottec.demoapp.fragment.TouchFragment;

/**
 * Created by qinbingbing on 3/31/16.
 */
public class DemoActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {


        return new TouchFragment();
    }
}
