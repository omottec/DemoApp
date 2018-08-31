package com.omottec.demoapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omottec.demoapp.R;
import com.omottec.demoapp.app.MyApplication;
import com.omottec.demoapp.utils.Logger;
import com.omottec.demoapp.utils.UiUtils;

/**
 * Created by qinbingbing on 31/08/2018.
 */

public class ScreenSizeFragment extends Fragment {
    public static final String TAG = "ScreenSizeFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_screen_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Logger.d(TAG, "onViewCreated");
        int width = UiUtils.getScreenSize(MyApplication.getContext(), true);
        int height = UiUtils.getScreenSize(MyApplication.getContext(), false);
        Logger.d(TAG, width + "*" + height);
    }
}
