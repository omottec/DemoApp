package com.omottec.demoapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.activity.SubProcActivity;
import com.omottec.demoapp.service.DemoService;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by qinbingbing on 4/19/16.
 */
public class MultiProcessFragment extends Fragment implements View.OnClickListener {
    private TextView mStartProcByService;
    private TextView mStartProcByActivity;
    private Context mContext;
    private Timer mTimer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_multi_process, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStartProcByService = view.findViewById(R.id.tv_start_proc_by_service);
        mStartProcByActivity = view.findViewById(R.id.tv_start_proc_by_activity);

        mStartProcByService.setOnClickListener(this);
        mStartProcByActivity.setOnClickListener(this);

    }

    private void startService() {
        Intent intent = new Intent(mContext, DemoService.class);
        mContext.startService(intent);
    }

    private void stopService() {
        Intent intent = new Intent(mContext, DemoService.class);
        mContext.stopService(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start_proc_by_service:
                startProcByService();
                break;
            case R.id.tv_start_proc_by_activity:
                mStartProcByActivity();
                break;
        }
    }

    private void mStartProcByActivity() {
        startActivity(new Intent(mContext, SubProcActivity.class));
    }

    private void startProcByService() {
        startService();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                stopService();
                SystemClock.sleep(3000);
                startService();
            }
        }, 3000);
    }
}
