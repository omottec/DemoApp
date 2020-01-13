package com.omottec.demoapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;

import com.omottec.demoapp.aspectj.AspectJFragment;
import com.omottec.demoapp.fragment.PowerOnOffFragment;
import com.omottec.demoapp.gson.GsonFragment;
import com.omottec.demoapp.job.JobFragment;
import com.omottec.demoapp.net.NetFragment;
import com.omottec.demoapp.net.OkhttpFragment;
import com.omottec.demoapp.utils.TimeLogger;

//import com.appsee.Appsee;

/**
 * Created by qinbingbing on 3/31/16.
 */
public class DemoActivity extends SingleFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        TimeLogger.methodStart();
        super.onCreate(savedInstanceState);
//        Appsee.start("c0a22dc0a69a4b8e84de241c5bfc1442");
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
//        return new SocketFragment();
//        return new OkhttpFragment();
//        return new AnrFragment();
//        return new RetrofitFragment();
//        return new IDataFragment();
//        return new NetFragment();
//        return new JobFragment();
//        return new PowerOnOffFragment();
//        return new AspectJFragment();
        return new GsonFragment();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.d(Tag.GESTURE, "dispatchTouchEvent " + TouchUtils.getTouchEventAction(ev));
        return super.dispatchTouchEvent(ev);
    }
}