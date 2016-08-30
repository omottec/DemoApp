package com.omottec.demoapp.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MotionEvent;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.fragment.TouchFragment;
import com.omottec.demoapp.utils.TouchUtils;

/**
 * Created by qinbingbing on 8/26/16.
 */
public class TouchActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = new TouchFragment();
            manager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(Tag.TOUCH, "TouchActivity.dispatchTouchEvent " + TouchUtils.getTouchEventAction(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(Tag.TOUCH, "TouchActivity.onTouchEvent " + TouchUtils.getTouchEventAction(event));
        return super.onTouchEvent(event);
    }
}
