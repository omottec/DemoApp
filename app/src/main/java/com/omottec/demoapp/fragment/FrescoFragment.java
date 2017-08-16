package com.omottec.demoapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 16/08/2017.
 */

public class FrescoFragment extends Fragment {
    private SimpleDraweeView mSdv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_fresco, container, false);
        mSdv = view.findViewById(R.id.sdv);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mSdv.setImageURI("http://p0.meituan.net/mallimages/dd50357c9fc0afa6740d3b57989214cd79343.jpg");
    }
}
