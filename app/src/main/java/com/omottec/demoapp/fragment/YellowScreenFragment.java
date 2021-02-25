package com.omottec.demoapp.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.omottec.demoapp.R;

public class YellowScreenFragment extends Fragment {
    public static final String TAG = "YellowScreenFragment";
    private TextView mTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_screen_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTv = view.findViewById(R.id.tv);
        mTv.setBackgroundColor(Color.YELLOW);
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getActivity().getWindow();
        Log.i(TAG, "onResume window:" + window);
        boolean viewAccelerated = mTv.isHardwareAccelerated();
        Log.i(TAG, "viewAccelerated:" + viewAccelerated);

    }
}
