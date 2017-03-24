package com.omottec.demoapp.view.ptr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.omottec.demoapp.R;
import com.omottec.demoapp.fragment.BaseFragment;

/**
 * Created by qinbingbing on 21/03/2017.
 */

public class PtrFragment extends BaseFragment {
    public static final String TAG = "PtrFragment";
    private LinearLayout mRootLl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View createContentView() {
        return View.inflate(mActivity, R.layout.f_padding, null);
    }
}
