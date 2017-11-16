package com.omottec.demoapp.lifecycle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.MemorySimulator;

/**
 * Created by qinbingbing on 6/8/16.
 */
public class LifeCycleActivity1 extends FragmentActivity {
    public static final String KEY_ID = "id";
    private static int count;
    private final int ID = count++;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Tag.ACTIVITY_LIFECYCLE, this + "|onCreate");
        if (savedInstanceState != null) {
            Log.d(Tag.ACTIVITY_LIFECYCLE, "savedInstanceState:" + savedInstanceState.hashCode());
            int id = savedInstanceState.getInt(KEY_ID);
            Log.d(Tag.ACTIVITY_LIFECYCLE, "getId:" + id + " from savedInstanceState in onCreate");
        }
        setContentView(R.layout.full_screen_text);
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText("LifeCycleActivity1" + "|id:" + ID);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLifeCycleActivity2();
            }
        });
    }

    private void startLifeCycleActivity2() {
        Intent intent = new Intent(this, LifeCycleActivity2.class);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(Tag.ACTIVITY_LIFECYCLE, this + "|onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Tag.ACTIVITY_LIFECYCLE, this + "|onStart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(Tag.ACTIVITY_LIFECYCLE, this + "|onRestoreInstanceState");
        Log.d(Tag.ACTIVITY_LIFECYCLE, "savedInstanceState:" + savedInstanceState.hashCode());
        int id = savedInstanceState.getInt(KEY_ID);
        Log.d(Tag.ACTIVITY_LIFECYCLE, "getId:" + id + " from savedInstanceState in onRestoreInstanceState");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Tag.ACTIVITY_LIFECYCLE, this + "|onResume");
//        MemorySimulator.drainMemoryDouble();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Tag.ACTIVITY_LIFECYCLE, this + "|onPause");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(Tag.ACTIVITY_LIFECYCLE, this + "|onSaveInstanceState");
        Log.d(Tag.ACTIVITY_LIFECYCLE, "outState:" + outState.hashCode());
        outState.putInt(KEY_ID, ID);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Tag.ACTIVITY_LIFECYCLE, this + "|onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Tag.ACTIVITY_LIFECYCLE, this + "|onDestroy");
    }
}
