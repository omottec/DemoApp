package com.omottec.demoapp.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.hook.ProxyResources;
import com.omottec.demoapp.hook.ResManager;
import com.omottec.logger.Logger;

public abstract class SingleFragmentActivity extends AppCompatActivity {
    private ProxyResources mProxyRes;
    private Resources mAppRes;

    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Logger.i(Tag.REPLACE_RES, "activity:" + this
            + ", BaseContext:" + getBaseContext()
            + ", ApplicationContext:" + getApplicationContext());
        AppCompatDelegate delegate = getDelegate();
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        Logger.i(Tag.REPLACE_RES, Logger.getInflaterInfo(layoutInflater));
        if (layoutInflater.getFactory2() == null) {
            LayoutInflaterCompat.setFactory2(layoutInflater, new LayoutInflater.Factory2() {
                @Override
                public View onCreateView(View parent,
                                         String name,
                                         Context context,
                                         AttributeSet attrs) {
                    View view = delegate.createView(parent, name, context, attrs);
                    int attributeCount = attrs.getAttributeCount();
                    Logger.i(Tag.REPLACE_RES, "factory2:" + this);
                    Logger.i(Tag.REPLACE_RES, "parent:" + parent
                        + ", view:" + view
                        + ", name:" + name
                        + ", context:" + context
                        + ", attrs:" + attrs
                        + ", attributeCount:" + attributeCount);

                    if (view == null
                        || mProxyRes == null
                        || !mProxyRes.resApkLoaded())
                        return view;

                    for (int i = 0; i < attributeCount; i++) {
                        int id = attrs.getAttributeResourceValue(i, 0);
                        String attributeName = attrs.getAttributeName(i);
                        String attributeValue = attrs.getAttributeValue(i);
                        Logger.i(Tag.REPLACE_RES, "id:" + id
                            + ", attributeName:" + attributeName
                            + ", attributeValue:" + attributeValue);
                        if ("text".equals(attributeName)
                            && view instanceof TextView
                            && mProxyRes.containId(id)) {
                            ((TextView) view).setText(mProxyRes.getText(id));
                        } else if ("src".equals(attributeName)
                            && view instanceof ImageView
                            && mProxyRes.containId(id)) {
                            ((ImageView) view).setImageResource(id);
                        } else if ("background".equals(attributeName)
                            && mProxyRes.containId(id)) {
                            Logger.i(Tag.REPLACE_RES, "contain background:" + attributeName);
                            view.setBackgroundResource(id);
                        }
                    }
                    return view;
                }

                @Override
                public View onCreateView(String name, Context context, AttributeSet attrs) {
                    return onCreateView(null, name, context, attrs);
                }
            });
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
            mProxyRes = new ProxyResources(mAppRes, ResManager.getInstance().getLoadedApkInfo());
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
