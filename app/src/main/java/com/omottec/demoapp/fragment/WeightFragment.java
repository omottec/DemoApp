package com.omottec.demoapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 16/07/2018.
 */

public class WeightFragment extends Fragment implements View.OnClickListener {
    private TextView mTv00;
    private TextView mTv01;
    private TextView mTv02;
    private TextView mTv03;
    private TextView mTv10;
    private TextView mTv11;
    private TextView mTv12;
    private TextView mTv13;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_weight, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTv00 = view.findViewById(R.id.tv00);
        mTv01 = view.findViewById(R.id.tv01);
        mTv02 = view.findViewById(R.id.tv02);
        mTv03 = view.findViewById(R.id.tv03);
        mTv10 = view.findViewById(R.id.tv10);
        mTv11 = view.findViewById(R.id.tv11);
        mTv12 = view.findViewById(R.id.tv12);
        mTv13 = view.findViewById(R.id.tv13);

        mTv10.setOnClickListener(this);
        mTv11.setOnClickListener(this);
        mTv12.setOnClickListener(this);
        mTv13.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv10:
                handleClick(mTv00);
                break;
            case R.id.tv11:
                handleClick(mTv01);
                break;
            case R.id.tv12:
                handleClick(mTv02);
                break;
            case R.id.tv13:
                handleClick(mTv03);
                break;
        }
    }

    private void handleClick(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        } else if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
        }
    }
}
