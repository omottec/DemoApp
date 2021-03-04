package com.omottec.demoapp.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.omottec.demoapp.R;

public class ReplaceResFragment extends Fragment {
    public static final String TAG = "ReplaceTextFragment";
    private TextView mBodyTv;
    private ImageView mTab1Iv;
    private ImageView mTab2Iv;
    private ImageView mTab3Iv;
    private ImageView mTab4Iv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_replace_res, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mBodyTv = view.findViewById(R.id.tv_body);
        mTab1Iv = view.findViewById(R.id.iv_tab_1);
        mTab2Iv = view.findViewById(R.id.iv_tab_2);
        mTab3Iv = view.findViewById(R.id.iv_tab_3);
        mTab4Iv = view.findViewById(R.id.iv_tab_4);


        mBodyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBodyTv.setText(R.string.dynamic_text_body_click);
            }
        });

        Glide.with(getActivity())
            .load("https://www.flaticon.com/svg/vstatic/svg/616/616430.svg?token=exp=1614841658~hmac=696c94acae5cf9e4c8f3415e6e1e481b")
            .into(mTab1Iv);
    }
}
