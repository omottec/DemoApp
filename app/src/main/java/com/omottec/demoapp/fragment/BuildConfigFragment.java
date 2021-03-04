package com.omottec.demoapp.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.BuildConfig;
import com.omottec.demoapp.R;

import java.util.Timer;

/**
 * Created by qinbingbing on 4/19/16.
 */
public class BuildConfigFragment extends Fragment {
    public static final String TAG = "BuildConfigFragment";
    private View mRootView;
    private TextView mTV;
    private Context mContext;
    private Timer mTimer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
        mTimer = new Timer();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.full_screen_text, null);
        mTV = (TextView) mRootView.findViewById(R.id.tv);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        Log.d(TAG, "BuildConfig.APPLICATION_ID:" + BuildConfig.APPLICATION_ID);
        Log.d(TAG, "BuildConfig.VERSION_NAME:" + BuildConfig.VERSION_NAME);
        Log.d(TAG, "BuildConfig.VERSION_CODE:" + BuildConfig.VERSION_CODE);
        Log.d(TAG, "BuildConfig.BUILD_TYPE:" + BuildConfig.BUILD_TYPE);
        Log.d(TAG, "BuildConfig.DEBUG:" + BuildConfig.DEBUG);
    }
}
