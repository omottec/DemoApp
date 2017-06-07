package com.omottec.demoapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.utils.MobileUtils;

/**
 * Created by qinbingbing on 07/06/2017.
 */

public class MobileInfoFragment extends Fragment {
    private TextView mTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.full_screen_text, container, false);
        mTv = (TextView) rootView.findViewById(R.id.tv);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTv.setText(MobileUtils.getDeviceId(getContext()));
    }
}
