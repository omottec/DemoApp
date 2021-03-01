package com.omottec.demoapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.omottec.demoapp.R;

public class ReplaceTextFragment extends Fragment {
    public static final String TAG = "ReplaceTextFragment";
    private TextView mBodyTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_replace_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mBodyTv = view.findViewById(R.id.tv_body);
        mBodyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBodyTv.setText(R.string.dynamic_text_body_click);
            }
        });
    }
}
