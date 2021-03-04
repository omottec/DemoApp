package com.omottec.demoapp.compact;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.utils.Views;

/**
 * Created by qinbingbing on 22/01/2018.
 */

public class ContextFragment extends Fragment {
    public static final String TAG = "ContextFragment";
    private TextView mTv;
    private AppCompatTextView mCompatTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_context, container, false);
        mTv = view.findViewById(R.id.tv);
        mCompatTv = view.findViewById(R.id.compat_tv);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTv.setText("TextView");
        mCompatTv.setText("AppCompatTextView");

//        Caused by: java.lang.ClassCastException: android.support.v7.widget.TintContextWrapper cannot be cast to android.app.Activity
//        Activity activity = (Activity) mCompatTv.getContext();

        Log.d(TAG, "mTv.getContext():" + mTv.getContext());
        Log.d(TAG, "mCompatTv.getContext():" + mCompatTv.getContext());
        Log.d(TAG, "Views.getActivity(mTv):" + Views.getActivity(mTv));
        Log.d(TAG, "Views.getActivity(mCompatTv):" + Views.getActivity(mCompatTv));
    }
}
