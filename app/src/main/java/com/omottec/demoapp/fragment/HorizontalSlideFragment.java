package com.omottec.demoapp.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.view.HorizontalSlideView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinbingbing on 8/30/16.
 */
public class HorizontalSlideFragment extends Fragment {
    private ViewGroup mRootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = new HorizontalSlideView(getContext());
        View pageView;
        for (int i = 0; i < 3; i++) {
            pageView = inflater.inflate(R.layout.slide_page, mRootView, false);
            initPageView(pageView, i);
            mRootView.addView(pageView);
        }
        return mRootView;
    }

    private void initPageView(View pageView, int index) {
        TextView tv = (TextView) pageView.findViewById(R.id.tv);
        ListView lv = (ListView) pageView.findViewById(R.id.lv);
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
    }
}
