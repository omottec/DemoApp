package com.omottec.demoapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MotionEvent;

import com.omottec.demoapp.Tag;
import com.omottec.demoapp.anr.AnrFragment;
import com.omottec.demoapp.fragment.MultiProcessFragment;
import com.omottec.demoapp.fragment.ScaleTypeFragment;
import com.omottec.demoapp.fragment.ScreenSizeFragment;
import com.omottec.demoapp.fragment.WeightFragment;
import com.omottec.demoapp.fresco.FrescoFragment;
import com.omottec.demoapp.image.TestIconDrawableFragment;
import com.omottec.demoapp.image.TestSplashDrawableFragment;
import com.omottec.demoapp.memory.MemoryFragment;
import com.omottec.demoapp.memory.MemoryInfoFragment;
import com.omottec.demoapp.net.OkhttpFragment;
import com.omottec.demoapp.net.SocketFragment;
import com.omottec.demoapp.net.UrlConnectionFragment;
import com.omottec.demoapp.permission.CallPhoneFragment;
import com.omottec.demoapp.permission.GetImeiFragment;
import com.omottec.demoapp.permission.NotificationFragment;
import com.omottec.demoapp.prop.PropFragment;
import com.omottec.demoapp.reuse.ReuseFragment;
import com.omottec.demoapp.root.RootFragment;
import com.omottec.demoapp.router.RouterFragment;
import com.omottec.demoapp.rxjava.RxJavaFragment;
import com.omottec.demoapp.spi.SpiFragment;
import com.omottec.demoapp.storage.StorageFragment;
import com.omottec.demoapp.utils.TimeLogger;
import com.omottec.demoapp.utils.TouchUtils;
import com.omottec.demoapp.v7.CardViewFragment;
import com.omottec.demoapp.view.coordinate.PositionFragment;
import com.omottec.demoapp.view.log.LogFragment;
import com.omottec.demoapp.view.recycler.PtrPicRecyclerFragment;
import com.omottec.demoapp.view.statuslayout.MultiPartStatusFragment;

import com.appsee.Appsee;

/**
 * Created by qinbingbing on 3/31/16.
 */
public class DemoActivity extends SingleFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        TimeLogger.methodStart();
        super.onCreate(savedInstanceState);
        Appsee.start("c0a22dc0a69a4b8e84de241c5bfc1442");
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
//        return new WeightFragment();
//        return new MultiPartStatusFragment();
//        return new RouterFragment();
//        return new ScreenSizeFragment();
//        return new ReuseFragment();
//        return new SpiFragment();
//        return new LogFragment();
//        return new RootFragment();
//        return new PropFragment();
//        return new RxJavaFragment();


        // Net
//        return new UrlConnectionFragment();
        return new SocketFragment();
//        return new OkhttpFragment();
//        return new AnrFragment();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.d(Tag.GESTURE, "dispatchTouchEvent " + TouchUtils.getTouchEventAction(ev));
        return super.dispatchTouchEvent(ev);
    }
}