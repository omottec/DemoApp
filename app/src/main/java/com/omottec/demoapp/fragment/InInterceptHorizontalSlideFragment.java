package com.omottec.demoapp.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.view.InInterceptHorizontalSlideView;
import com.omottec.demoapp.view.InInterceptListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinbingbing on 8/30/16.
 */
public class InInterceptHorizontalSlideFragment extends Fragment {
    private InInterceptHorizontalSlideView mRootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Tag.MEASURE, "InInterceptHorizontalSlideFragment.onCreate uiThread:" + (Looper.myLooper() == Looper.getMainLooper()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(Tag.MEASURE, "InInterceptHorizontalSlideFragment.onCreateView uiThread:" + (Looper.myLooper() == Looper.getMainLooper()));
        mRootView = new InInterceptHorizontalSlideView(getContext());
        View pageView;
        for (int i = 0; i < 3; i++) {
            pageView = inflater.inflate(R.layout.in_intercept_slide_page, mRootView, false);
            initPageView(pageView, i);
            mRootView.addView(pageView);
        }
        return mRootView;
    }

    private void initPageView(View pageView, int index) {
        TextView tv = (TextView) pageView.findViewById(R.id.tv);
        InInterceptListView lv = (InInterceptListView) pageView.findViewById(R.id.lv);
        tv.setBackgroundColor(Color.rgb(255 / (index + 1), 255 / (index + 1), 255));
        tv.setText("Page " + index);
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 50; i++)
            items.add("item " + i);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                items);
        lv.setAdapter(adapter);
        lv.setInInterceptHorizontalSideView(mRootView);
    }

    @Override
    public void onResume() {
        Log.d(Tag.MEASURE, "InInterceptHorizontalSlideFragment.onResume uiThread:" + (Looper.myLooper() == Looper.getMainLooper()));
        super.onResume();
        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d(Tag.MEASURE, "onGlobalLayout");
                mRootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                Log.d(Tag.MEASURE, "Measured: " + mRootView.getMeasuredWidth() + "*" + mRootView.getMeasuredHeight());
                Log.d(Tag.MEASURE, mRootView.getWidth() + "*" + mRootView.getHeight());
            }
        });
        mRootView.post(new Runnable() {
            @Override
            public void run() {
                Log.d(Tag.MEASURE, "Runnable run");
                Log.d(Tag.MEASURE, "Measured: " + mRootView.getMeasuredWidth() + "*" + mRootView.getMeasuredHeight());
                Log.d(Tag.MEASURE, mRootView.getWidth() + "*" + mRootView.getHeight());
            }
        });
    }

}
