package com.omottec.demoapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.utils.UiUtils;
import com.omottec.logger.Logger;

/**
 * Created by qinbingbing on 31/01/2018.
 */

public class DisplayMetricsFragment extends Fragment {
    public static final String TAG = "DisplayMetricsFragment";
    private TextView mTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.full_screen_text, container, false);
        mTv = view.findViewById(R.id.tv);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        String s = UiUtils.getDisplayMetrics(getActivity()).toString();
        mTv.setText(s);
        Logger.d(TAG, s);
    }
}
