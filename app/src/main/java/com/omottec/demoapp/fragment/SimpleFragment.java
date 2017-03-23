package com.omottec.demoapp.fragment;

import android.view.View;

import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 22/03/2017.
 */

public class SimpleFragment extends BaseFragment {
    @Override
    protected View createContentView() {
        return View.inflate(mActivity, R.layout.f_simple, null);
    }
}
