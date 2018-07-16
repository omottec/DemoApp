package com.omottec.demoapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.omottec.demoapp.fragment.ScaleTypeFragment;
import com.omottec.demoapp.fragment.WeightFragment;
import com.omottec.demoapp.fresco.FrescoFragment;
import com.omottec.demoapp.image.TestIconDrawableFragment;
import com.omottec.demoapp.image.TestSplashDrawableFragment;
import com.omottec.demoapp.memory.MemoryFragment;
import com.omottec.demoapp.memory.MemoryInfoFragment;
import com.omottec.demoapp.permission.CallPhoneFragment;
import com.omottec.demoapp.permission.GetImeiFragment;
import com.omottec.demoapp.permission.NotificationFragment;
import com.omottec.demoapp.storage.StorageFragment;
import com.omottec.demoapp.utils.TimeLogger;
import com.omottec.demoapp.v7.CardViewFragment;
import com.omottec.demoapp.view.coordinate.PositionFragment;
import com.omottec.demoapp.view.recycler.PtrPicRecyclerFragment;

/**
 * Created by qinbingbing on 3/31/16.
 */
public class DemoActivity extends SingleFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        TimeLogger.methodStart();
        super.onCreate(savedInstanceState);
        TimeLogger.methodEnd();
    }

    @Override
    protected void onResume() {
        TimeLogger.methodStart();
        super.onResume();
        TimeLogger.methodEnd();
        TimeLogger.dump();
    }

    @Override
    protected Fragment createFragment() {
//        return new ScaleTypeFragment();
//        return new StorageFragment();
//        return new GetImeiFragment();
//        return new CardViewFragment();
//        return new MemoryFragment();
//        return new MemoryInfoFragment();
//        return new NotificationFragment();
//        return new PtrPicRecyclerFragment();
//        return new PositionFragment();
//        return new FrescoFragment();
        return new WeightFragment();
    }
}