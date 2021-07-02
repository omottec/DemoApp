package com.omottec.demoapp.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import androidx.fragment.app.Fragment;
import com.omottec.demoapp.utils.TimeLogger;
import com.omottec.demoapp.utils.UiUtils;

//import com.appsee.Appsee;

/**
 * Created by qinbingbing on 3/31/16.
 */
public class DemoActivity extends SingleFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        TimeLogger.methodStart();
        //LayoutInflater inflater = getLayoutInflater();
        //Logger.i(Tag.REPLACE_RES, "before super.onCreate " + Logger.getInflaterInfo(inflater));
        super.onCreate(savedInstanceState);
        //LayoutInflater inflater1 = getLayoutInflater();
        //Logger.i(Tag.REPLACE_RES, "after super.onCreate " + Logger.getInflaterInfo(inflater1));
        //Logger.i(Tag.REPLACE_RES, "getResources:" + getResources());
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
        //return new ScaleTypeFragment();
        //return new StorageFragment();
        //return new GetImeiFragment();
        //return new CardViewFragment();

        //perf
        //return new MemoryFragment();
        //return new MemoryInfoFragment();
        //return new AshmemClientFragment();
        //return new ProfiloFragment();
        //return new DumpHeapFragment();
        //return new StackSizeFragment();
        //return new AnrFragment();
        //return new MultiProcessFragment();
        //return new LooperFragment();
        //return new BitmapFragment();
        //return new CpuFragment();
        //return new GpuFragment();
        //return new YellowScreenFragment();
        //return new ReplaceResFragment();


        //return new NotificationFragment();
        //return new PtrPicRecyclerFragment();
        //return new PositionFragment();
        //return new FrescoFragment();
        //return new WeightFragment();
        //return new MultiPartStatusFragment();
        //return new RouterFragment();
        //return new ScreenSizeFragment();
        //return new ReuseFragment();
        //return new SpiFragment();
        //return new LogFragment();
        //return new RootFragment();
        //return new PropFragment();
        //return new RxJavaFragment();
        //return new LogTagFragment();

        // Net
        //return new UrlConnectionFragment();
        //return new SocketFragment();
        //return new OkhttpFragment();
        //return new RetrofitFragment();
        //return new NetFragment();

        //return new GsonFragment();

        //return new IDataFragment();
        //return new PowerOnOffFragment();
        //return new AspectJFragment();
        return new AsmFragment();

        //return new JobFragment();
        //return new ThreadPoolFragment();

        //return new ApiFragment();

        //return new KtFragment();

        // Lang
        //return new LangFragment();

        // IO
        //return new FileFragment();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.d(Tag.GESTURE, "dispatchTouchEvent " + TouchUtils.getTouchEventAction(ev));
        return super.dispatchTouchEvent(ev);
    }
}