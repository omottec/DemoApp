package com.omottec.demoapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 11/11/16.
 */

public class TitleBarActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_text);
    }
}
