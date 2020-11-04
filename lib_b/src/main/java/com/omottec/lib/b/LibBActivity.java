package com.omottec.lib.b;

import android.app.Activity;
import android.os.Bundle;
import com.google.gson.Gson;

public class LibBActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Gson gson = new Gson();
    }
}
