package com.omottec.demoapp.permission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.utils.Logger;

import java.util.Arrays;

/**
 * Created by omottec on 09/01/2018.
 */

public class CallPhoneFragment extends Fragment {
    public static final String TAG = CallPhoneFragment.class.getSimpleName();
    public static final int REQUEST_CODE_PERMISSION_CALL_PHONE = 1;
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
        mTv.setOnClickListener(v -> {
            int selfPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);
            Logger.d(TAG, "checkSelfPermission result:" + selfPermission);
            if (PackageManager.PERMISSION_GRANTED == selfPermission) {
                Logger.d(TAG, "PERMISSION_GRANTED startCallActivity");
                startCallActivity();
            } else {
                Logger.d(TAG, "PERMISSION_DENIED requestPermissions");
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_PERMISSION_CALL_PHONE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Logger.d(TAG, "onRequestPermissionsResult requestCode:" + requestCode
                + ", permissions:" + Arrays.toString(permissions)
                + ", grantResults:" + Arrays.toString(grantResults));
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCallActivity();
                } else {

                }
                break;
            default:
                break;
        }
    }

    private void startCallActivity() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:10010"));
        startActivity(intent);
    }
}
