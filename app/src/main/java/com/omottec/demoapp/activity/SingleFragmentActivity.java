package com.omottec.demoapp.activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.collection.ArrayMap;
import androidx.core.view.LayoutInflaterCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.hook.LoadedApkInfo;
import com.omottec.demoapp.hook.ProxyResources;
import com.omottec.demoapp.hook.ReplaceResFactory2;
import com.omottec.demoapp.hook.ResManager;
import com.omottec.logger.Logger;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public abstract class SingleFragmentActivity extends AppCompatActivity {
    private ProxyResources mProxyRes;
    private Resources mAppRes;


    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Logger.i(Tag.REPLACE_RES, "activity:" + this
            + ", BaseContext:" + getBaseContext()
            + ", ApplicationContext:" + getApplicationContext());
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        Logger.i(Tag.REPLACE_RES, "Activity layoutInflater:" + Logger.getInflaterInfo(layoutInflater));
        if (layoutInflater.getFactory2() == null) {
            LayoutInflaterCompat.setFactory2(layoutInflater, new ReplaceResFactory2(getDelegate()));
        }
        super.onCreate(savedInstanceState);
        onCreate();
//        onCreateV1();
    }

    private void onCreate() {
        setContentView(R.layout.activity_fragment);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        Logger.i(Tag.REPLACE_RES, Logger.getInflaterInfo(layoutInflater));

        //setContentView(LayoutInflater.from(this).inflate(R.layout.activity_fragment, null));
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = createFragment();
            manager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

    private void onCreateV1() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = createFragment();
        manager.beginTransaction()
                .add(android.R.id.content, fragment)
                .commit();
    }

    @Override
    public Resources getResources() {
        if (mProxyRes == null) {
            mAppRes = super.getResources();
            Logger.i(Tag.REPLACE_RES, "getResources appResources:" + mAppRes);
            LoadedApkInfo loadedApkInfo = ResManager.getInstance().getLoadedApkInfo();
            //try {
            //    AssetManager assets = mAppRes.getAssets();
            //    Method addAssetPath = assets.getClass().getDeclaredMethod("addAssetPath", String.class);
            //    addAssetPath.setAccessible(true);
            //    addAssetPath.invoke(assets, loadedApkInfo.apkPath);
            //    mProxyRes = new ProxyResources(assets, mAppRes, loadedApkInfo);
            //} catch (Exception e) {
            //    Logger.e(Tag.REPLACE_RES, e);
            //    mProxyRes = new ProxyResources(mAppRes, loadedApkInfo);
            //}

            mProxyRes = new ProxyResources(mAppRes, loadedApkInfo);
            Logger.i(Tag.REPLACE_RES, "getResources mProxyRes:" + mProxyRes);
        }
        return mProxyRes;
        //return ResManager.getInstance().getProxyRes();
    }

    public Resources getAppRes() {
        return mAppRes;
    }

    @Override
    public Resources.Theme getTheme() {
        //Resources.Theme theme = getResources().newTheme();
        //theme.applyStyle(R.style.AppTheme, true);
        //return theme;

        return super.getTheme();
    }
}
