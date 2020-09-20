package com.omottec.demoapp.cpu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.omottec.demoapp.R;

public class CpuFragment extends Fragment {
    private TextView mTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.full_screen_text, container, false);
        mTv = view.findViewById(R.id.tv);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTv.setText("device64:" + CpuUtils.isDevice64()
            + ", app64:" + CpuUtils.isApp64());
    }
}
