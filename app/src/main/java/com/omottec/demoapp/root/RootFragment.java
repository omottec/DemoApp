package com.omottec.demoapp.root;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

/**
 * Created by qinbingbing on 06/11/2018.
 */

public class RootFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "RootFragment";
    public static final String TARGET_APP_PATH = "/sdcard/app-launcher-debug.apk";
    public static final String LAUNCHER_APP_PATH = "/sdcard/";
    private TextView mRootTv;
    private TextView mInstallTv;
    private TextView mUninstallTv;
    private TextView mLauncherTv;
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
        mLauncherTv = view.findViewById(R.id.tv_launcher);

        mRootTv.setOnClickListener(this);
        mInstallTv.setOnClickListener(this);
        mUninstallTv.setOnClickListener(this);
        mLauncherTv.setOnClickListener(this);
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
            case R.id.tv_launcher:
                onClickLauncher();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean isRetailLauncherDefault = isRetailLauncherDefault();
        Log.d(TAG, "onResume isRetailLauncherDefault:" + isRetailLauncherDefault);
        if (!isRetailLauncherDefault)
            startRetailLauncher();
    }

    private boolean isRetailLauncherDefault() {
        Log.d(TAG, "isRetailLauncherDefault");
        PackageManager pm = getContext().getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo == null || resolveInfo.activityInfo == null) return false;
        return "com.meituan.retail.launcher".equals(resolveInfo.activityInfo.packageName);
    }

    private void startRetailLauncher() {
        Log.d(TAG, "startRetailLauncher");
        PackageManager pm = getContext().getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, PackageManager.MATCH_ALL);
        if (resolveInfos == null || resolveInfos.size() <= 0) return;

        Log.d(TAG, Arrays.toString(resolveInfos.toArray()));
        ResolveInfo retailInfo = null;
        for (int i = 0; i < resolveInfos.size(); i++) {
            if (resolveInfos.get(i) == null || resolveInfos.get(i).activityInfo == null) continue;
            if ("com.meituan.retail.launcher".equals(resolveInfos.get(i).activityInfo.packageName)) {
                retailInfo = resolveInfos.get(i);
                break;
            }
        }

        Log.d(TAG, "retailInfo:" + retailInfo);
        if (retailInfo == null) return;
        Intent launcherIntent = new Intent(Intent.ACTION_MAIN);
        launcherIntent.addCategory(Intent.CATEGORY_HOME);
        launcherIntent.addCategory(Intent.CATEGORY_DEFAULT);
        launcherIntent.setClassName(retailInfo.activityInfo.packageName, retailInfo.activityInfo.name);
        launcherIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(launcherIntent);
    }

    private void setDefaultLauncher() {
        Log.d(TAG, "setDefaultLauncher");
        PackageManager pm = getContext().getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, PackageManager.MATCH_ALL);
        Log.d(TAG, Arrays.toString(resolveInfos.toArray()));
        if (resolveInfos == null || resolveInfos.size() <= 1) return;

        List<ComponentName> componentNames = new ArrayList<>();
        ComponentName defaultComName = null;
        for (ResolveInfo resolveInfo : resolveInfos) {
            if (resolveInfo == null || resolveInfo.activityInfo == null) continue;
            componentNames.add(new ComponentName(resolveInfo.activityInfo.packageName,
                    resolveInfo.activityInfo.name));


        }
        if (componentNames.size() <= 1) return;

        for (ComponentName componentName : componentNames) {
            if ("com.meituan.retail.launcher".equals(componentName.getPackageName())) {
                defaultComName = new ComponentName(componentName.getPackageName(), componentName.getClassName());
            } else {
//                pm.clearPackagePreferredActivities(componentName.getPackageName());
            }
        }
        IntentFilter filter = new IntentFilter(Intent.ACTION_MAIN);
        filter.addCategory(Intent.CATEGORY_HOME);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        ComponentName[] components = new ComponentName[componentNames.size()];
        componentNames.toArray(components);
//        pm.addPreferredActivity(filter, IntentFilter.MATCH_CATEGORY_EMPTY, components, defaultComName);
    }

    private void onClickLauncher() {
        Log.d(TAG, "onClickLauncher");
        startRetailLauncher();
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
                    List<String> strings = RootTools.sendShell("pm install -t -r " + TARGET_APP_PATH, 10 * 60 * 1000);
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
