package com.omottec.demoapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;

import com.facebook.profilo.controllers.external.api.ExternalTraceControl;
import com.omottec.demoapp.anr.AnrFragment;
import com.omottec.demoapp.aspectj.AspectJFragment;
import com.omottec.demoapp.concurrent.ThreadPoolFragment;
import com.omottec.demoapp.fragment.LooperFragment;
import com.omottec.demoapp.fragment.MultiProcessFragment;
import com.omottec.demoapp.fragment.PowerOnOffFragment;
import com.omottec.demoapp.gson.GsonFragment;
import com.omottec.demoapp.job.JobFragment;
import com.omottec.demoapp.memory.ashmem.AshmemClientFragment;
import com.omottec.demoapp.net.NetFragment;
import com.omottec.demoapp.net.OkhttpFragment;
import com.omottec.demoapp.perf.ProfiloFragment;
import com.omottec.demoapp.utils.TimeLogger;
import com.omottec.demoapp.view.recycler.PtrPicRecyclerFragment;

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

//        perf
//        return new MemoryFragment();
//        return new MemoryInfoFragment();
//        return new AshmemClientFragment();
//        return new ProfiloFragment();
//        return new AnrFragment();
//        return new MultiProcessFragment();
//        return new LooperFragment();


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
//        return new RetrofitFragment();
//        return new NetFragment();

//        return new GsonFragment();

//        return new IDataFragment();
//        return new PowerOnOffFragment();
//        return new AspectJFragment();


//        return new JobFragment();
        return new ThreadPoolFragment();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.d(Tag.GESTURE, "dispatchTouchEvent " + TouchUtils.getTouchEventAction(ev));
        return super.dispatchTouchEvent(ev);
    }
}