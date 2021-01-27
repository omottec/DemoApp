package com.omottec.demoapp.permission;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.logger.Logger;
import com.tbruyelle.rxpermissions.RxPermissions;

/**
 * Created by omottec on 09/01/2018.
 */

public class RxCallPhoneFragment extends Fragment {
    public static final String TAG = RxCallPhoneFragment.class.getSimpleName();
    private TextView mTv;
    private RxPermissions mRxPermissions;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRxPermissions = new RxPermissions(getActivity());
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
            mRxPermissions.request(Manifest.permission.CALL_PHONE)
                    .subscribe(granted -> {
                        Logger.d(TAG, "granted:" + granted);
                        if (granted) {
                            startCallActivity();
                        } else {

                        }
                    });
//            startCallActivity();
        });
    }

    private void startCallActivity() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:10010"));
        startActivity(intent);
    }
}
