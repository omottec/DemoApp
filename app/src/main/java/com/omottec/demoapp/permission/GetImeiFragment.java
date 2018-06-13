package com.omottec.demoapp.permission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.utils.Logger;

import java.util.Arrays;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by omottec on 09/01/2018.
 */

public class GetImeiFragment extends Fragment {
    public static final String TAG = GetImeiFragment.class.getSimpleName();
    public static final int REQUEST_CODE_PERMISSION_READ_PHONE_STATE = 1;
    private TextView mTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_screen_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTv = view.findViewById(R.id.tv);
        mTv.setText("GetImeiFragment");
        mTv.setOnClickListener(v -> {
            int selfPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE);
            Logger.d(TAG, "ContextCompat.checkSelfPermission READ_PHONE_STATE result:" + selfPermission);
            /*if (PackageManager.PERMISSION_GRANTED == selfPermission) {
                getImei();
            } else {
                Logger.d(TAG, "requestPermissions");
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_PERMISSION_READ_PHONE_STATE);
            }*/
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Logger.d(TAG, "onRequestPermissionsResult requestCode:" + requestCode
                + ", permissions:" + Arrays.toString(permissions)
                + ", grantResults:" + Arrays.toString(grantResults));
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {

                }
                break;
            default:
                break;
        }
    }

    private void getImei() {
        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(TELEPHONY_SERVICE);
        if (telephonyManager == null) return;
        @SuppressLint("MissingPermission") String deviceId = telephonyManager.getDeviceId();
        Logger.d(TAG, "deviceId:" + deviceId);
    }
}
