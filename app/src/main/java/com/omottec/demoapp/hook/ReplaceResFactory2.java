package com.omottec.demoapp.hook;

import android.content.Context;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.collection.ArrayMap;
import com.omottec.demoapp.Tag;
import com.omottec.logger.Logger;
import java.lang.reflect.Constructor;
import java.util.Map;

public class ReplaceResFactory2 implements LayoutInflater.Factory2 {
    private static final Class<?>[] sConstructorSignature = new Class[]{
        Context.class, AttributeSet.class};
    private static final Map<String, Constructor<? extends View>> sConstructorMap
        = new ArrayMap<>();
    private static final String[] sClassPrefixList = {
        "android.widget.",
        "android.view.",
        "android.webkit."
    };
    private AppCompatDelegate mDelegate;
    private final Object[] mConstructorArgs = new Object[2];

    public ReplaceResFactory2(AppCompatDelegate delegate) {
        this.mDelegate = delegate;
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent,
                             @NonNull String name,
                             @NonNull Context context,
                             @NonNull AttributeSet attrs) {
        View view = mDelegate.createView(parent, name, context, attrs);
        int attributeCount = attrs.getAttributeCount();
        Logger.i(Tag.REPLACE_RES, "parent:" + parent
            + ", view:" + view
            + ", name:" + name
            + ", context:" + context
            + ", attrs:" + attrs
            + ", attributeCount:" + attributeCount);


        if (!(context.getResources() instanceof ProxyResources))
            return view;
        ProxyResources proxyRes = (ProxyResources) context.getResources();
        if (!proxyRes.resApkLoaded()) return view;

        if (view == null)
            view = createViewFromTag(context, name, attrs);

        Logger.i(Tag.REPLACE_RES, "view:" + view);
        if (view == null)
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
                && proxyRes.containId(id)) {
                ((TextView) view).setText(proxyRes.getText(id));
            } else if ("src".equals(attributeName)
                && view instanceof ImageView
                && proxyRes.containId(id)) {
                ((ImageView) view).setImageResource(id);
            } else if ("background".equals(attributeName)
                && proxyRes.containId(id)) {
                Logger.i(Tag.REPLACE_RES, "contain background:" + attributeName);
                view.setBackgroundResource(0);
                view.setBackgroundResource(id);
            }
        }
        return view;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name,
                             @NonNull Context context,
                             @NonNull AttributeSet attrs) {
        return onCreateView(null, name, context, attrs);
    }


    private View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if (name.equals("view")) {
            name = attrs.getAttributeValue(null, "class");
        }

        try {
            mConstructorArgs[0] = context;
            mConstructorArgs[1] = attrs;

            if (-1 == name.indexOf('.')) {
                for (int i = 0; i < sClassPrefixList.length; i++) {
                    final View view = createViewByPrefix(context, name, sClassPrefixList[i]);
                    if (view != null) {
                        return view;
                    }
                }
                return null;
            } else {
                return createViewByPrefix(context, name, null);
            }
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            Logger.e(Tag.REPLACE_RES, e);
            return null;
        } finally {
            // Don't retain references on context.
            mConstructorArgs[0] = null;
            mConstructorArgs[1] = null;
        }
    }

    private View createViewByPrefix(Context context, String name, String prefix)
        throws ClassNotFoundException, InflateException {
        Constructor<? extends View> constructor = sConstructorMap.get(name);

        try {
            if (constructor == null) {
                // Class not found in the cache, see if it's real, and try to add it
                Class<? extends View> clazz = context.getClassLoader().loadClass(
                    prefix != null ? (prefix + name) : name).asSubclass(View.class);

                constructor = clazz.getConstructor(sConstructorSignature);
                sConstructorMap.put(name, constructor);
            }
            constructor.setAccessible(true);
            return constructor.newInstance(mConstructorArgs);
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            Logger.e(Tag.REPLACE_RES, e);
            return null;
        }
    }
}
