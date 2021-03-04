package com.omottec.demoapp.activity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.omottec.demoapp.R;

public abstract class SingleFragmentActivity extends FragmentActivity {
    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
