package com.omottec.demoapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.omottec.demoapp.R;
import com.omottec.demoapp.app.MyApplication;
import com.omottec.demoapp.utils.ClickUtils;

/**
 * Created by qinbingbing on 16/11/2017.
 */

public class FastClickFragment extends Fragment {
    private TextView mTv;
    private TextView mTv1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.two_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTv = view.findViewById(R.id.tv);
        mTv1 = view.findViewById(R.id.tv1);

        mTv.setOnClickListener(v -> {
            if (ClickUtils.isFastDoubleClick())
                Toast.makeText(MyApplication.getContext(), "fast double click", Toast.LENGTH_SHORT).show();
        });
        mTv1.setOnClickListener(v -> {
            if (ClickUtils.isFastFiveClick())
                Toast.makeText(MyApplication.getContext(), "fast five click", Toast.LENGTH_SHORT).show();
        });
    }
}
