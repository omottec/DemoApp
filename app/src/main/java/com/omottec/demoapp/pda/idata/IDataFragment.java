package com.omottec.demoapp.pda.idata;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.omottec.demoapp.R;
import com.omottec.demoapp.app.MyApplication;

public class IDataFragment extends Fragment {
    public static final String TAG = "IDataFragment";
    private int count;
    private Runnable mToastRunnable = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "mToastRunnable " + count);
            Toast.makeText(getActivity(), "Toast " + (count++), Toast.LENGTH_LONG).show();
            MyApplication.getUiHandler().postDelayed(this, 1000);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_screen_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MyApplication.getUiHandler().postDelayed(mToastRunnable, 1000);
    }
}
