package com.omottec.demoapp.anr;

import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Printer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.omottec.demoapp.R;
import com.omottec.demoapp.utils.Logger;

/**
 * Created by omottec on 19/01/2019.
 */

public class AnrFragment extends Fragment {
    public static final String TAG = "AnrFragment";
    private long mMsgDispatchTime;
    private long mMsgFinishTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_screen_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "click", Toast.LENGTH_SHORT).show();
                while (true) {
                    v.invalidate();
                }
            }
        });

    }

    private void detectAnr() {
        Looper.getMainLooper().setMessageLogging(new Printer() {
            @Override
            public void println(String x) {
                Logger.i(TAG, x);
                if (x != null && x.startsWith(">>>>> Dispatching to"))
                    mMsgDispatchTime = SystemClock.elapsedRealtime();
                if (x != null && x.startsWith("<<<<< Finished to"))
                    mMsgFinishTime = SystemClock.elapsedRealtime();
                if (mMsgFinishTime >= mMsgDispatchTime && mMsgDispatchTime > 0)
                    Logger.i(TAG, "handle msg cost " + (mMsgFinishTime - mMsgDispatchTime) + " ms");
            }
        });
    }
}
