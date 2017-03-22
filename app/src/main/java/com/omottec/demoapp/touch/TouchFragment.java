package com.omottec.demoapp.touch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 8/26/16.
 */
public class TouchFragment extends Fragment {
    private TouchRelativeLayout mRl;
    private TouchTextView mTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRl = (TouchRelativeLayout) inflater.inflate(R.layout.f_touch, null);
        mTv = (TouchTextView) mRl.findViewById(R.id.touch_tv);
        return mRl;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
