package com.omottec.demoapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.logger.Logger;

public abstract class SingleFragmentActivity extends AppCompatActivity {
    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate delegate = getDelegate();
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        if (layoutInflater.getFactory2() == null) {
            LayoutInflaterCompat.setFactory2(layoutInflater, new LayoutInflater.Factory2() {
                @Override
                public View onCreateView(View parent,
                                         String name,
                                         Context context,
                                         AttributeSet attrs) {
                    Logger.i(Tag.REPLACE_RES, "parent:" + parent
                        + ", name:" + name
                        + ", context:" + context
                        + ", attrs:" + attrs);
                    int attributeCount = attrs.getAttributeCount();
                    for (int i = 0; i < attributeCount; i++) {
                        Logger.i(Tag.REPLACE_RES, "attributeName:" + attrs.getAttributeName(i)
                            + ", attributeValue:" + attrs.getAttributeValue(i));
                    }
                    View view = delegate.createView(parent, name, context, attrs);
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
}
