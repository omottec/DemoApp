package com.omottec.demoapp.perf;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.profilo.controllers.external.api.ExternalTraceControl;
import com.omottec.demoapp.R;

public class ProfiloFragment extends Fragment {
    private TextView mTv;
    private int count;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_screen_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTv = view.findViewById(R.id.tv);
        mTv.setText("Stop Profilo");
        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Stop Profilo " + (++count), Toast.LENGTH_SHORT).show();
                if (count == 3)
                    ExternalTraceControl.stopTrace();
            }
        });
    }
}
