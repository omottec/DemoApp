package com.omottec.demoapp.root;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.io.IoUtils;
import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.RootToolsException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private TextView mStartLauncherTv;
    private TextView mSetLauncherTv;
    private TextView mSetAccessibility;

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
        mStartLauncherTv = view.findViewById(R.id.tv_start_launcher);
        mSetLauncherTv = view.findViewById(R.id.tv_set_launcher);
        mSetAccessibility = view.findViewById(R.id.tv_set_accessibility);

        mRootTv.setOnClickListener(this);
        mInstallTv.setOnClickListener(this);
        mUninstallTv.setOnClickListener(this);
        mStartLauncherTv.setOnClickListener(this);
        mSetLauncherTv.setOnClickListener(this);
        mSetAccessibility.setOnClickListener(this);
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
            case R.id.tv_start_launcher:
                onClickStartLauncher();
                break;
            case R.id.tv_set_launcher:
                onClickSetLauncher();
                break;
            case R.id.tv_set_accessibility:
                onClickSetAccessibility();
                break;
        }
    }

    private void onClickSetLauncher() {
        Log.d(TAG, "onClickSetLauncher");
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
                pm.clearPackagePreferredActivities(componentName.getPackageName());
            }
        }
        IntentFilter filter = new IntentFilter(Intent.ACTION_MAIN);
        filter.addCategory(Intent.CATEGORY_HOME);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        ComponentName[] components = new ComponentName[componentNames.size()];
        componentNames.toArray(components);
        pm.addPreferredActivity(filter, IntentFilter.MATCH_CATEGORY_EMPTY, components, defaultComName);
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean isRetailLauncherDefault = isRetailLauncherDefault();
        Log.d(TAG, "onResume isRetailLauncherDefault:" + isRetailLauncherDefault);
        if (!isRetailLauncherDefault) {

        }
    }

    private void onClickSetAccessibility() {
        Log.d(TAG, "onClickSetAccessibility");

        /*Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/

        int accessibilityEnabled = 0;
        final String service = getContext().getPackageName() + "/" + MyAccessibilityService.class.getCanonicalName();  //这里改成自己的class
        try {
            accessibilityEnabled = Settings.Secure.getInt(getContext().getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException ignored) {
            Log.e(TAG, "Settings.Secure.getInt", ignored);
        }
        Log.d(TAG, "accessibilityEnabled:" + accessibilityEnabled);
        boolean myAccessibilityServiceEnable = false;
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString(getContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();
                    Log.d(TAG, "accessibilityService:" + accessibilityService);
                    if (service.equals(accessibilityService)) {
                        myAccessibilityServiceEnable = true;
                    }
                }
            }
        }

        if (!myAccessibilityServiceEnable) {
            try {
                RootTools.debugMode = true;
                /*List<String> stringList = RootTools.sendShell(
                        new String[]{"settings put secure enabled_accessibility_services com.omottec.demoapp/com.omottec.demoapp.root.MyAccessibilityService"},
                        0,
                        null,
                        false,
                        10 * 60 * 1000);*/
                List<String> stringList = RootTools.sendShell("settings put secure enabled_accessibility_services com.omottec.demoapp/com.omottec.demoapp.root.MyAccessibilityService", 10*60*1000);
                Log.d(TAG, Arrays.toString(stringList.toArray()));
                List<String> stringList1 = RootTools.sendShell("settings put secure accessibility_enabled 1", 10*60*1000);
                Log.d(TAG, Arrays.toString(stringList1.toArray()));

            } catch (IOException e) {
                e.printStackTrace();
            } catch (RootToolsException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }

            /*String cmd = "settings put secure enabled_accessibility_services com.omottec.demoapp/com.omottec.demoapp.root.MyAccessibilityService";
            String cmd1 = "settings put secure accessibility_enabled 1";
            execShell(cmd);
            execShell(cmd1);*/
        }
    }

    private void execShell(String cmd) {
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "start exec " + cmd);
                    Process p = Runtime.getRuntime().exec(cmd);
                    BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String readLine = br.readLine();
                    while(readLine != null){
                        Log.d(TAG, readLine);
                        readLine = br.readLine();
                    }
                    if( br != null) {
                        IoUtils.close(br);
                    }
                    p.destroy();
                    p = null;
                    Log.d(TAG, "executed " + cmd);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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

    private void onClickStartLauncher() {
        Log.d(TAG, "onClickStartLauncher");
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
