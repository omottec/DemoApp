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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_traverse_view);
        mTraverseBreadth = (TextView) findViewById(R.id.tv_traverse_breadth);
        mTraverseDepth = (TextView) findViewById(R.id.tv_traverse_depth);

        mTraverseBreadth.setOnClickListener(this);
        mTraverseDepth.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        traverseViewDepth(getWindow().getDecorView());
    }

    private void traverseViewDepth(View view) {
        if (view == null) return;
        Logger.i(TAG, view.toString());

        if (!(view instanceof ViewGroup)) return;
        ViewGroup group = (ViewGroup) view;

        for (int i = 0; i < group.getChildCount(); i++)
            traverseViewDepth(group.getChildAt(i));
    }

    private void traverseViewBreadth(View... views) {
        if (views == null || views.length == 0) return;
        for (View view : views)
            Logger.i(TAG, view.toString());
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
        list.addLast(root);
        while (list.size() > 0) {
            View view = list.pollFirst();
            Logger.i(TAG, view.toString());
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
                traverseBreadthByList(getWindow().getDecorView());
//                traverseViewBreadth(getWindow().getDecorView());
                break;
            case R.id.tv_traverse_depth:
                traverseViewDepth(getWindow().getDecorView());
                break;
        }
    }
}
