package com.omottec.lib.a;

import android.app.Activity;
import android.os.Bundle;
import com.google.gson.Gson;

public class LibAActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Gson();
    }
}
