package com.omottec.demoapp.root;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.RootToolsException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

/**
 * Created by qinbingbing on 06/11/2018.
 */

public class RootFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "RootFragment";
    public static final String PATH = "/sdcard/app-develop-release-retail-3.8.4-10379.apk";
    private TextView mRootTv;
    private TextView mInstallTv;
    private TextView mUninstallTv;
    private ExecutorService mExecutor = Executors.newCachedThreadPool();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_root, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreated");
        mRootTv = view.findViewById(R.id.tv_root);
        mInstallTv = view.findViewById(R.id.tv_install);
        mUninstallTv = view.findViewById(R.id.tv_uninstall);

        mRootTv.setOnClickListener(this);
        mInstallTv.setOnClickListener(this);
        mUninstallTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_root:
                onClickRoot();
                break;
            case R.id.tv_install:
                onClickInstall();
                break;
            case R.id.tv_uninstall:
                onClickUninstall();
                break;
        }
    }

    private void onClickRoot() {
        Log.d(TAG, "onClickRoot");
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean rootAvailable = RootTools.isRootAvailable();
                    Log.d(TAG, "rootAvailable:" + rootAvailable);
                    boolean accessGiven = RootTools.isAccessGiven();
                    Log.d(TAG, "accessGiven:" + accessGiven);
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void onClickUninstall() {
        Log.d(TAG, "onClickUninstall");
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> strings = RootTools.sendShell("pm uninstall com.meituan.retail.c.android", 10 * 60 * 1000);
                    Log.d(TAG, Arrays.toString(strings.toArray()));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (RootToolsException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void onClickInstall() {
        Log.d(TAG, "onClickInstall");
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> strings = RootTools.sendShell("pm install -r " + PATH, 10 * 60 * 1000);
                    Log.d(TAG, Arrays.toString(strings.toArray()));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (RootToolsException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
