package com.omottec.demoapp.aspectj;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.logger.Logger;

public class AspectJFragment extends Fragment {
    private TextView mTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_screen_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTv = view.findViewById(R.id.tv);
        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produceException();
            }
        });

        People people = new People();
    }

    private void produceException() {
        try {
            throw new IllegalStateException("manual exception");
        } catch (Exception e) {
            Logger.e(Tag.ASPECTJ, e);
        }
    }
}
