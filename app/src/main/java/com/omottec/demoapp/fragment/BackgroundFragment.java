package com.omottec.demoapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 26/09/2017.
 */

public class BackgroundFragment extends Fragment implements View.OnClickListener {
    private View mRootView;
    private RelativeLayout mLeftRl;
    private RelativeLayout mRightRl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.f_background, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mLeftRl = (RelativeLayout) view.findViewById(R.id.rl_left);
        mRightRl = (RelativeLayout) view.findViewById(R.id.rl_right);
        mLeftRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_left:
                mLeftRl.setBackgroundResource(android.R.color.holo_red_light);
                mRightRl.setBackgroundResource(android.R.color.holo_green_light);
                break;
        }
    }
}
