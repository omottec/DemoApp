package com.omottec.demoapp.lifecycle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.fragment.SimpleTextFragment;

/**
 * Created by qinbingbing on 18/09/2017.
 */

public class SimpleTextActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(Tag.LIFECYCLE_A_F, "onCreate start " + this);
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.a_simple_text1);
        setContentView(R.layout.a_simple_text);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_container, SimpleTextFragment.newInstance("aaa"))
                .commit();
        Log.d(Tag.LIFECYCLE_A_F, "onCreate end " + this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Tag.LIFECYCLE_A_F, "onStart " + this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Tag.LIFECYCLE_A_F, "onResume " + this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Tag.LIFECYCLE_A_F, "onPause " + this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Tag.LIFECYCLE_A_F, "onStop " + this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Tag.LIFECYCLE_A_F, "onDestroy " + this);
    }
}
