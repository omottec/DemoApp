package com.omottec.demoapp.cpu;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.omottec.demoapp.R;

public class CpuFragment extends Fragment implements View.OnClickListener {
    private TextView mCpuTv;
    private TextView mBuildTv;
    private TextView mProcTv;
    private TextView mAppInfoTv;
    private TextView mProcessTv;
    private TextView mClassLoaderTv;
    private TextView mSystemTv;
    private TextView mAppInfoApp64Tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_cpu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mCpuTv = view.findViewById(R.id.tv_cpu);
        mBuildTv = view.findViewById(R.id.tv_build);
        mProcTv = view.findViewById(R.id.tv_proc);
        mAppInfoTv = view.findViewById(R.id.tv_app_info);
        mProcessTv = view.findViewById(R.id.tv_process);
        mClassLoaderTv = view.findViewById(R.id.tv_class_loader);
        mSystemTv = view.findViewById(R.id.tv_system);
        mAppInfoApp64Tv = view.findViewById(R.id.tv_app_info_app_64);

        mCpuTv.setOnClickListener(this);
        mBuildTv.setOnClickListener(this);
        mProcTv.setOnClickListener(this);
        mAppInfoTv.setOnClickListener(this);
        mProcessTv.setOnClickListener(this);
        mClassLoaderTv.setOnClickListener(this);
        mSystemTv.setOnClickListener(this);
        mAppInfoApp64Tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cpu:
                mCpuTv.setText("mCpuTv device64:" + CpuUtils.isDevice64()
                    + ", app64:" + CpuUtils.isApp64(getContext()));
                break;
            case R.id.tv_build:
                mBuildTv.setText("mBuildTv device64:" + CpuUtils.isDevice64ByBuild());
                break;
            case R.id.tv_proc:
                mProcTv.setText("mProcTv device64:" + CpuUtils.isDevice64ByProc());
                break;
            case R.id.tv_app_info:
                mAppInfoTv.setText("mAppInfoTv device64:" + CpuUtils.isDevice64ByAppInfo(getContext()));
                break;
            case R.id.tv_process:
                mProcessTv.setText("mProcessTv app64:" + CpuUtils.isApp64ByProcess());
                break;
            case R.id.tv_class_loader:
                mClassLoaderTv.setText("mClassLoaderTv app64:" + CpuUtils.isApp64ByClassLoader());
                break;
            case R.id.tv_system:
                mSystemTv.setText("mSystemTv app64:" + CpuUtils.isApp64BySystem());
                break;
            case R.id.tv_app_info_app_64:
                mAppInfoApp64Tv.setText("mAppInfoApp64Tv app64:" + CpuUtils.isApp64ByAppInfo(getContext()));
                break;
        }
    }
}
