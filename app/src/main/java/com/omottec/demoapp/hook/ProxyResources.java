package com.omottec.demoapp.hook;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.app.MyApplication;
import com.omottec.logger.Logger;
import java.io.File;

public class ProxyResources extends Resources {
    private Resources mLoadedRes;
    private String mLoadedPkgName;

    @NonNull
    @Override
    public CharSequence getText(int id) throws NotFoundException {
        logRes(this, id);
        if (resApkLoaded()) {
            int loadedId = convertId(id);
            if (loadedId != 0) {
                logRes(mLoadedRes, loadedId);
                return mLoadedRes.getText(loadedId);
            }
        }
        return super.getText(id);
    }

    @Override
    public Drawable getDrawable(int id, @Nullable Theme theme) throws NotFoundException {
        logRes(this, id);
        if (resApkLoaded()) {
            int loadedId = convertId(id);
            if (loadedId != 0) {
                logRes(mLoadedRes, loadedId);
                return mLoadedRes.getDrawable(loadedId);
            }
        }
        return super.getDrawable(id, theme);
    }

    public int convertId(int appId) {
        if (!resApkLoaded()) return 0;
        return mLoadedRes.getIdentifier(getResourceEntryName(appId), getResourceTypeName(appId), mLoadedPkgName);
    }

    public boolean containId(int appId) {
        return convertId(appId) != 0;
    }

    @Override
    public int getColor(int id, @Nullable Theme theme) throws NotFoundException {
        logRes(this, id);
        if (resApkLoaded()) {
            int loadedId = convertId(id);
            if (loadedId != 0) {
                logRes(mLoadedRes, loadedId);
                return mLoadedRes.getColor(loadedId);
            }
        }
        return super.getColor(id, theme);
    }

    private void logRes(Resources resources, int id) {
        String resourceName = resources.getResourceName(id);
        String resourcePackageName = resources.getResourcePackageName(id);
        String resourceTypeName = resources.getResourceTypeName(id);
        String resourceEntryName = resources.getResourceEntryName(id);
        Logger.i(Tag.REPLACE_RES,
            new StringBuilder("resourceName:").append(resourceName)
                .append(", id:").append(id)
                .append(", resourcePackageName:").append(resourcePackageName)
                .append(", resourceTypeName:").append(resourceTypeName)
                .append(", resourceEntryName:").append(resourceEntryName)
                .toString());
    }

    public boolean resApkLoaded() {
        return mLoadedRes != null && mLoadedPkgName != null;
    }

    public ProxyResources(Resources appRes, Resources loadedRes, String loadedPkgName) {
        super(appRes.getAssets(), appRes.getDisplayMetrics(), appRes.getConfiguration());
        mLoadedRes = loadedRes;
        mLoadedPkgName = loadedPkgName;
    }

    public ProxyResources(Resources appRes, LoadedApkInfo loadedApkInfo) {
        super(appRes.getAssets(), appRes.getDisplayMetrics(), appRes.getConfiguration());
        if (loadedApkInfo != null) {
            mLoadedRes = loadedApkInfo.resources;
            mLoadedPkgName = loadedApkInfo.pkgName;
        }
    }

    public ProxyResources(AssetManager asset, Resources appRes, LoadedApkInfo loadedApkInfo) {
        super(asset, appRes.getDisplayMetrics(), appRes.getConfiguration());
        if (loadedApkInfo != null) {
            mLoadedRes = loadedApkInfo.resources;
            mLoadedPkgName = loadedApkInfo.pkgName;
        }
    }

    private static ProxyResources sInstance;

    public static ProxyResources getInstance(Resources appRes) {
        if (sInstance == null) {
            synchronized (ProxyResources.class) {
                sInstance = new ProxyResources(appRes);
            }
        }
        return sInstance;
    }



    private ProxyResources(Resources appRes) {
        super(appRes.getAssets(), appRes.getDisplayMetrics(), appRes.getConfiguration());
    }

    public void setAppRes(Resources appRes) {

    }


    public void setLoadedRes(Resources loadedRes) {
        mLoadedRes = loadedRes;
    }

    public void setLoadedPkgName(String loadedPkgName) {
        mLoadedPkgName = loadedPkgName;
    }

    public static Pair<Resources, String> getLoadedResources(Resources appRes) {
        Context appContext = MyApplication.getContext();
        PackageManager pm = appContext.getPackageManager();
        String loadedApkPath = appContext.getFilesDir() +  "/res.zip";
        Logger.i(Tag.REPLACE_RES, "loadedApkPath:" + loadedApkPath);
        Logger.i(Tag.REPLACE_RES, "apk exists:" + new File(loadedApkPath).exists());
        PackageInfo packageInfo =
            pm.getPackageArchiveInfo(loadedApkPath, PackageManager.GET_ACTIVITIES);
        Logger.i(Tag.REPLACE_RES, "packageInfo:" + packageInfo);
        if (packageInfo == null) return new Pair<>(null, null);
        packageInfo.applicationInfo.sourceDir = loadedApkPath;
        packageInfo.applicationInfo.publicSourceDir = loadedApkPath;
        try {
            Resources res = pm.getResourcesForApplication(packageInfo.applicationInfo);
            return new Pair<>(new Resources(res.getAssets(), appRes.getDisplayMetrics(), appRes.getConfiguration()),
                packageInfo.packageName);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e(Tag.REPLACE_RES, e);
            return new Pair<>(null, null);
        }
    }

}
