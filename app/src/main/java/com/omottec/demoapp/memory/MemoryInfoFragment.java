package com.omottec.demoapp.memory;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.utils.Logger;

/**
 * Created by qinbingbing on 27/06/2018.
 */

public class MemoryInfoFragment extends Fragment {
    public static final String TAG = "MemoryInfoFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_screen_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Logger.d(TAG, "onViewCreated view:" + view);
        TextView tv = view.findViewById(R.id.tv);
        String memoryInfo = MemoryUtils.getMemoryInfo();
        tv.setText(memoryInfo);
        Logger.d(TAG, memoryInfo);
    }
}
