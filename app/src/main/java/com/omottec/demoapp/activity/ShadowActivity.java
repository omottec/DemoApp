package com.omottec.demoapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.omottec.demoapp.R;
import com.omottec.demoapp.fragment.ShadowEntranceFragment;

/**
 * Created by qinbingbing on 10/25/16.
 */

public class ShadowActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int shadowKey = getIntent().getExtras().getInt(ShadowEntranceFragment.SHADOW_KEY);
        switch (shadowKey) {
            case ShadowEntranceFragment.MAP_SHADOW_FLOAT:
                setContentView(R.layout.a_map_shadow_float);
                break;
            case ShadowEntranceFragment.MAP_SHADOW_INSERT:
                setContentView(R.layout.a_map_shadow_insert);
                break;
            case ShadowEntranceFragment.NORMAL_SHADOW_FLOAT:
                setContentView(R.layout.a_normal_shadow_float);
                break;
            case ShadowEntranceFragment.NORMAL_SHADOW_INSERT:
                setContentView(R.layout.a_normal_shadow_insert);
                break;
            case ShadowEntranceFragment.NORMAL_SHADOW_INSERT_FLOAT:
                setContentView(R.layout.a_normal_shadow_insert_float);
                break;
        }
    }
}
