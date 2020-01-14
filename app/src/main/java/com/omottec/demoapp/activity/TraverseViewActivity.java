package com.omottec.demoapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.utils.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TraverseViewActivity extends Activity implements View.OnClickListener {
    public static final String TAG = "TraverseViewActivity";
    private TextView mTraverseBreadth;
    private TextView mTraverseDepth;
    private TextView mTraverseBreadthList;
    private TextView mTraverseDepthList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_traverse_view);
        mTraverseBreadth = (TextView) findViewById(R.id.tv_traverse_breadth);
        mTraverseDepth = (TextView) findViewById(R.id.tv_traverse_depth);
        mTraverseBreadthList = findViewById(R.id.tv_traverse_breadth_list);
        mTraverseDepthList = findViewById(R.id.tv_traverse_depth_list);

        mTraverseBreadth.setOnClickListener(this);
        mTraverseDepth.setOnClickListener(this);
        mTraverseBreadthList.setOnClickListener(this);
        mTraverseDepthList.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private String getViewStr(View view) {
        if (view.getTag() != null)
            return view.getTag().toString();
        return view.toString();
    }

    private void traverseViewDepth(View view) {
        if (view == null) return;
        Logger.i(TAG, getViewStr(view));

        if (!(view instanceof ViewGroup)) return;
        ViewGroup group = (ViewGroup) view;

        for (int i = 0; i < group.getChildCount(); i++)
            traverseViewDepth(group.getChildAt(i));
    }

    private void traverseViewBreadth(View... views) {
        if (views == null || views.length == 0) return;
        for (View view : views)
            Logger.i(TAG, getViewStr(view));
        Logger.i(TAG, "======================");
        List<View> list = new ArrayList<>();
        for (View view : views) {
            if (!(view instanceof ViewGroup)) continue;
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++)
                list.add(group.getChildAt(i));
        }
        traverseViewBreadth(list.toArray(new View[]{}));
    }

    private void traverseBreadthByList(View root) {
        if (root == null) return;
        LinkedList<View> list = new LinkedList<>();
        list.offerLast(root);
        while (list.size() > 0) {
            View view = list.pollFirst();
            Logger.i(TAG, getViewStr(view));
            if (view instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) view;
                for (int i = 0; i < group.getChildCount(); i++)
                    list.offerLast(group.getChildAt(i));
            }
        }
    }

    private void traverseDepthByList(View root) {
        if (root == null) return;
        LinkedList<View> list = new LinkedList<>();
        list.offerLast(root);
        while (list.size() > 0) {
            View view = list.pollLast();
            Logger.i(TAG, getViewStr(view));
            if (view instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) view;
                for (int i = 0; i < group.getChildCount(); i++)
                    list.offerLast(group.getChildAt(i));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_traverse_breadth:
                traverseViewBreadth(getWindow().getDecorView());
                break;
            case R.id.tv_traverse_depth:
                traverseViewDepth(getWindow().getDecorView());
                break;
            case R.id.tv_traverse_breadth_list:
                traverseBreadthByList(getWindow().getDecorView());
                break;
            case R.id.tv_traverse_depth_list:
                traverseDepthByList(getWindow().getDecorView());
                break;
        }
    }
}
