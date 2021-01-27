package com.omottec.demoapp.fragment;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.logger.Logger;

public class ApiFragment extends Fragment {
    public static final String TAG = "ApiFragment";
    private TextView mTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_screen_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTv = view.findViewById(R.id.tv);
        appInfo();
    }

    private void appInfo() {
        ApplicationInfo applicationInfo = getContext().getApplicationInfo();
        StringBuilder dirInfo = new StringBuilder("applicationInfo.sourceDir:")
                .append(applicationInfo.sourceDir)
                .append('\n')
                .append("applicationInfo.dataDir:")
                .append(applicationInfo.dataDir);
        Logger.i(TAG, dirInfo.toString());
        mTv.setText(dirInfo.toString());
    }
}
