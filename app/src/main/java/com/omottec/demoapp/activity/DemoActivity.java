package com.omottec.demoapp.activity;

import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;

import com.omottec.demoapp.GpuFragment;
import com.omottec.demoapp.cpu.CpuFragment;
import com.omottec.demoapp.fragment.MultiProcessFragment;
import com.omottec.demoapp.kt.KtFragment;
import com.omottec.demoapp.log.LogTagFragment;
import com.omottec.demoapp.memory.BitmapFragment;
import com.omottec.demoapp.memory.DumpHeapFragment;
import com.omottec.demoapp.memory.MemoryInfoFragment;
import com.omottec.demoapp.memory.StackSizeFragment;
import com.omottec.demoapp.anr.AnrFragment;
import com.omottec.demoapp.aspectj.AspectJFragment;
import com.omottec.demoapp.concurrent.ThreadPoolFragment;
import com.omottec.demoapp.fragment.ApiFragment;
import com.omottec.demoapp.fragment.PowerOnOffFragment;
import com.omottec.demoapp.gson.GsonFragment;
import com.omottec.demoapp.job.JobFragment;
import com.omottec.demoapp.memory.ashmem.AshmemClientFragment;
import com.omottec.demoapp.net.NetFragment;
import com.omottec.demoapp.net.OkhttpFragment;
import com.omottec.demoapp.utils.TimeLogger;
import com.omottec.demoapp.utils.UiUtils;
import com.omottec.demoapp.view.statuslayout.MultiPartStatusFragment;

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
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                UiUtils.bfsByDeque(getWindow().getDecorView());
                //Debug.stopMethodTracing();
            }
        }, 5000);
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
//        return new DumpHeapFragment();
//        return new StackSizeFragment();
//        return new AnrFragment();
//        return new MultiProcessFragment();
//        return new LooperFragment();
//        return new BitmapFragment();
//        return new CpuFragment();
        return new GpuFragment();


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
//        return new LogTagFragment();

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
//        return new ThreadPoolFragment();

        //return new ApiFragment();

        //return new KtFragment();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.d(Tag.GESTURE, "dispatchTouchEvent " + TouchUtils.getTouchEventAction(ev));
        return super.dispatchTouchEvent(ev);
    }
}