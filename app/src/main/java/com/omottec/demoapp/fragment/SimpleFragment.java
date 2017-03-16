package com.omottec.demoapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.text.TextUtilsCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.BuildConfig;
import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;

import java.util.Timer;

/**
 * Created by qinbingbing on 4/19/16.
 */
public class SimpleFragment extends Fragment {
    public static final String TAG = "SimpleFragment";
    public static final String ARG_NAME = "arg_name";
    private View mRootView;
    private TextView mTV;
    private String mName;

    public static SimpleFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        SimpleFragment fragment = new SimpleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mName = getArguments().getString(ARG_NAME);
        Log.d(Tag.FRAME_TAB_PAGER, "onAttach " + this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Tag.FRAME_TAB_PAGER, "onCreate " + this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(Tag.FRAME_TAB_PAGER, "onCreateView " + this);
        mRootView = inflater.inflate(R.layout.full_screen_text, null);
        mTV = (TextView) mRootView.findViewById(R.id.tv);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(Tag.FRAME_TAB_PAGER, "onViewCreated " + this);
        mTV.setText(TextUtils.isEmpty(mName) ? "???" : mName);
        mTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(Tag.FRAME_TAB_PAGER, "onDestroyView " + this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(Tag.FRAME_TAB_PAGER, "onDestroy " + this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(Tag.FRAME_TAB_PAGER, "onDetach " + this);
    }

    @Override
    public String toString() {
        return mName + " " + super.toString();
    }
}
