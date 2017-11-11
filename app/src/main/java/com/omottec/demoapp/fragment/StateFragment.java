package com.omottec.demoapp.fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 11/11/2017.
 */

public class StateFragment extends Fragment {
    private Button mBtn;
    private Button mBtn1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.two_btn, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mBtn = view.findViewById(R.id.btn);
        mBtn1 = view.findViewById(R.id.btn1);

        ColorDrawable enable = new ColorDrawable(Color.parseColor("#FF0000"));
        ColorDrawable press = new ColorDrawable(Color.parseColor("#00FF00"));
        ColorDrawable disable = new ColorDrawable(Color.parseColor("#0000FF"));
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{-android.R.attr.state_enabled}, disable);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, press);
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, enable);
        mBtn.setBackground(stateListDrawable);

        int[][] states = new int[3][];
        states[0] = new int[]{-android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_pressed};
        states[2] = new int[]{android.R.attr.state_enabled};
        int[] colors = {Color.parseColor("#FF0000"), Color.parseColor("#0000FF"), Color.parseColor("#00FF00")};
        ColorStateList colorStateList = new ColorStateList(states, colors);
        mBtn.setTextColor(colorStateList);

        mBtn.setEnabled(false);

        mBtn1.setOnClickListener(v -> {
            mBtn.setEnabled(!mBtn.isEnabled());
            Toast.makeText(v.getContext(), "mBtn.isEnabled:" + mBtn.isEnabled(), Toast.LENGTH_SHORT).show();
        });
    }
}
