package com.omottec.demoapp.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.omottec.demoapp.R;
import com.omottec.demoapp.app.MyApplication;

/**
 * Created by qinbingbing on 02/11/2017.
 */

public class Fragment24 extends Fragment {
    public static final String TAG = "Fragment24";
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
            boolean notificationsEnabled = NotificationManagerCompat.from(MyApplication.getContext()).areNotificationsEnabled();
            int sdkInt = Build.VERSION.SDK_INT;
            Log.d(TAG, "sdkInt:" + sdkInt + ", notificationsEnabled:" + notificationsEnabled);
            /*if (sdkInt >= Build.VERSION_CODES.N
                    && !notificationsEnabled) {
                new AlertDialog.Builder(Fragment24.this.getActivity()).setMessage("请打开push通知开关").show();
                return;
            }*/
            Toast.makeText(MyApplication.getContext(), "对不起，找不到安装文件，请到官网下载安装最新版本~", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
