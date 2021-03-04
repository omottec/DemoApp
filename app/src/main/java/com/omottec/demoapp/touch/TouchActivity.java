package com.omottec.demoapp.touch;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import android.util.Log;
import android.view.MotionEvent;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
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
        boolean b = super.dispatchTouchEvent(ev);
        Log.d(Tag.TOUCH, "TouchActivity.dispatchTouchEvent " + TouchUtils.getTouchEventAction(ev) + " " + b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(Tag.TOUCH, "TouchActivity.onTouchEvent " + TouchUtils.getTouchEventAction(event));
        boolean b = super.onTouchEvent(event);
        Log.d(Tag.TOUCH, "TouchActivity.onTouchEvent " + TouchUtils.getTouchEventAction(event) + " " + b);
        return b;
    }
}
