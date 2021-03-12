package com.omottec.demoapp.hook;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.Pair;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.app.MyApplication;
import com.omottec.logger.Logger;
import java.io.File;

public final class ResManager {
    private ResManager() {}

    private static final class Singleton {
        private static final ResManager INSTANCE = new ResManager();
    }

    public static ResManager getInstance() {
        return Singleton.INSTANCE;
    }

    private ProxyResources mProxyRes;

    public void setProxyResources(ProxyResources proxyRes) {
        mProxyRes = proxyRes;
    }

    public ProxyResources getProxyRes() {
        return mProxyRes;
    }

    private LoadedApkInfo mLoadedApkInfo;

    public void loadApk(Resources appRes) {
        Context appContext = MyApplication.getContext();
        PackageManager pm = appContext.getPackageManager();
        String loadedApkPath = appContext.getFilesDir() +  "/res.zip";
        Logger.i(Tag.REPLACE_RES, "loadedApkPath:" + loadedApkPath);
        Logger.i(Tag.REPLACE_RES, "apk exists:" + new File(loadedApkPath).exists());
        PackageInfo packageInfo =
            pm.getPackageArchiveInfo(loadedApkPath, PackageManager.GET_ACTIVITIES);
        Logger.i(Tag.REPLACE_RES, "packageInfo:" + packageInfo);
        if (packageInfo == null) return;
        packageInfo.applicationInfo.sourceDir = loadedApkPath;
        packageInfo.applicationInfo.publicSourceDir = loadedApkPath;
        try {
            mLoadedApkInfo = new LoadedApkInfo();
            mLoadedApkInfo.resources = new Resources(
                pm.getResourcesForApplication(packageInfo.applicationInfo).getAssets(),
                appRes.getDisplayMetrics(), appRes.getConfiguration());
            mLoadedApkInfo.pkgName = packageInfo.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e(Tag.REPLACE_RES, e);
        }
    }

    public LoadedApkInfo getLoadedApkInfo() {
        return mLoadedApkInfo;
    }

    public LoadedApkInfo getLoadedApkInfo(Resources appRes) {
        Context appContext = MyApplication.getContext();
        PackageManager pm = appContext.getPackageManager();
        String loadedApkPath = appContext.getFilesDir() +  "/res.zip";
        Logger.i(Tag.REPLACE_RES, "loadedApkPath:" + loadedApkPath);
        Logger.i(Tag.REPLACE_RES, "apk exists:" + new File(loadedApkPath).exists());
        PackageInfo packageInfo =
            pm.getPackageArchiveInfo(loadedApkPath, PackageManager.GET_ACTIVITIES);
        Logger.i(Tag.REPLACE_RES, "packageInfo:" + packageInfo);
        if (packageInfo == null) return null;
        packageInfo.applicationInfo.sourceDir = loadedApkPath;
        packageInfo.applicationInfo.publicSourceDir = loadedApkPath;
        try {
            LoadedApkInfo loadedApkInfo = new LoadedApkInfo();
            loadedApkInfo.resources = new Resources(
                pm.getResourcesForApplication(packageInfo.applicationInfo).getAssets(),
                appRes.getDisplayMetrics(), appRes.getConfiguration());
            loadedApkInfo.pkgName = packageInfo.packageName;
            return loadedApkInfo;
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e(Tag.REPLACE_RES, e);
            return null;
        }
    }
}
