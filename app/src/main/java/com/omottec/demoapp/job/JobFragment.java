package com.omottec.demoapp.job;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;

public class JobFragment extends Fragment {
    private TextView mWakeLockTv;
    private TextView mPullServerTv;
    private ComponentName mServiceComponent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_job, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mWakeLockTv = view.findViewById(R.id.tv_wake_lock);
        mPullServerTv = view.findViewById(R.id.tv_pull_server);

        mServiceComponent = new ComponentName(getContext(), MyJobService.class);
        Intent startServiceIntent = new Intent(getContext(), MyJobService.class);
        getContext().startService(startServiceIntent);

        mPullServerTv.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                pollServer();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void pollServer() {
        JobScheduler jobScheduler = (JobScheduler) getContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        for (int i = 0; i < 10; i++) {
            JobInfo jobInfo = new JobInfo.Builder(i, mServiceComponent)
                    .setMinimumLatency(5 * 1000)
                    .setOverrideDeadline(60 * 1000)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .build();
            mWakeLockTv.append("Scheduling job " + i + "\n");
            jobScheduler.schedule(jobInfo);
        }
    }
}
