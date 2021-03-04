package com.omottec.demoapp.lifecycle;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;

/**
 * Created by qinbingbing on 6/8/16.
 */
public class LifeCycleActivity2 extends FragmentActivity {
    private static int count;
    private final int ID = count++;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Tag.ACTIVITY_LIFECYCLE, this + "|onCreate");
        setContentView(R.layout.full_screen_text);
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setBackgroundColor(Color.GREEN);
        tv.setText("LifeCycleActivity2" + "|" + "id:" + ID);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Tag.ACTIVITY_LIFECYCLE, this + "|onResume");
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
