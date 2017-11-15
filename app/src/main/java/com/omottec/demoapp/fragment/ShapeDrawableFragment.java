package com.omottec.demoapp.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.app.MyApplication;
import com.omottec.demoapp.utils.UiUtils;

import java.util.Random;

/**
 * Created by qinbingbing on 14/11/2017.
 */

public class ShapeDrawableFragment extends Fragment {
    public static final String TAG = "ShapeDrawableFragment";
    private TextView mTv;
    private TextView mTv1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_shape_drawable, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTv = view.findViewById(R.id.tv);
        mTv1 = view.findViewById(R.id.tv1);
        mTv1.setText(Html.fromHtml("<font color=\"#FFFFFF\">DemoApp</font>"));
        Drawable background = mTv.getBackground();
        Log.d(TAG, "background:" + background);

        mTv1.setOnClickListener(v -> {
            if (background instanceof GradientDrawable) {
                Random random = new Random();
                if (random.nextBoolean()) {
                    mTv.setTextColor(Color.RED);
                    GradientDrawable gradientDrawable = (GradientDrawable) background;
                    gradientDrawable.setColor(Color.GREEN);
                    gradientDrawable.setStroke(UiUtils.dip2px(MyApplication.getContext(), 5), Color.BLUE);
                } else {
                    mTv.setTextColor(Color.GREEN);
                    GradientDrawable gradientDrawable = (GradientDrawable) background;
                    gradientDrawable.setColor(Color.BLUE);
                    gradientDrawable.setStroke(UiUtils.dip2px(MyApplication.getContext(), 5), Color.RED);
                }
            }
        });
    }
}
