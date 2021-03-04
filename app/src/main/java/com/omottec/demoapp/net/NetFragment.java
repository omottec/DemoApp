package com.omottec.demoapp.net;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.omottec.demoapp.R;

public class NetFragment extends Fragment {
    public static final String TAG = "NetFragment";
    private TextView mTv;
    private TextView mTv1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_wifi, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTv = view.findViewById(R.id.tv);
        mTv1 = view.findViewById(R.id.tv1);

        String netInfo = NetUtils.getNetInfo(getContext());
        Log.i(TAG, netInfo);
        mTv.setText(netInfo);

        String wifiInfo = NetUtils.getWifiInfo(getContext());
        Log.i(TAG, wifiInfo);
        mTv1.setText(wifiInfo);

        Toast.makeText(getContext(), netInfo, Toast.LENGTH_LONG).show();
    }
}
