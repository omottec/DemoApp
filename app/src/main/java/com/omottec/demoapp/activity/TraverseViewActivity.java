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
    private TextView mTraverseWidthByArray;
    private TextView mTraverseDepthByRecursive;
    private TextView mTraverseWidthByDeque;
    private TextView mTraverseDepthByDeque;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_traverse_view);
        mTraverseWidthByArray = (TextView) findViewById(R.id.tv_traverse_width_by_array);
        mTraverseDepthByRecursive = (TextView) findViewById(R.id.tv_traverse_depth_by_recursive);
        mTraverseWidthByDeque = findViewById(R.id.tv_traverse_width_by_deque);
        mTraverseDepthByDeque = findViewById(R.id.tv_traverse_depth_by_deque);

        mTraverseWidthByArray.setOnClickListener(this);
        mTraverseDepthByRecursive.setOnClickListener(this);
        mTraverseWidthByDeque.setOnClickListener(this);
        mTraverseDepthByDeque.setOnClickListener(this);
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

    // 可能有栈溢出问题
    private void traverseDepthByRecursive(View view) {
        if (view == null) return;
        Logger.i(TAG, getViewStr(view));

        if (!(view instanceof ViewGroup)) return;
        ViewGroup group = (ViewGroup) view;
        for (int i = 0; i < group.getChildCount(); i++)
            traverseDepthByRecursive(group.getChildAt(i));
    }

    // 内存性能不好
    private void traverseWidthByArray(View... views) {
        if (views == null || views.length == 0) return;
        // 打印父层
        for (View view : views)
            Logger.i(TAG, getViewStr(view));
        Logger.i(TAG, "======================");

        // 打印子层
        List<View> list = new ArrayList<>();
        for (View view : views) {
            if (!(view instanceof ViewGroup)) continue;
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++)
                list.add(group.getChildAt(i));
        }
        traverseWidthByArray(list.toArray(new View[]{}));
    }

    // 利用Deque深度遍历二叉树，操作双端
    private void traversWidthByDeque(View root) {
        if (root == null) return;
        // 需要借助双端队列实现，移除父节点时把子节点添加进来
        LinkedList<View> queue = new LinkedList<>();
        // add remove get VS offer poll peek
        queue.offerFirst(root);
        while (!queue.isEmpty()) {
            View view = queue.pollLast();
            Logger.i(TAG, getViewStr(view));
            if (view instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) view;
                for (int i = 0; i < group.getChildCount(); i++)
                    queue.offerFirst(group.getChildAt(i));
            }
        }
    }

    // 利用Deque深度遍历二叉树，只操作单端
    private void traverseDepthByDeque(View root) {
        if (root == null) return;
        // 需要借助双端队列实现，移除父节点时把子节点添加进来
        LinkedList<View> queue = new LinkedList<>();
        // add remove get VS offer poll peek
        queue.offerLast(root);
        while (!queue.isEmpty()) {
            View view = queue.pollLast();
            Logger.i(TAG, getViewStr(view));
            if (view instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) view;
                for (int i = group.getChildCount() - 1; i >= 0; i--)
                    queue.offerLast(group.getChildAt(i));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_traverse_width_by_array:
                traverseWidthByArray(getWindow().getDecorView());
                break;
            case R.id.tv_traverse_width_by_deque:
                traversWidthByDeque(getWindow().getDecorView());
                break;
            case R.id.tv_traverse_depth_by_recursive:
                traverseDepthByRecursive(getWindow().getDecorView());
                break;
            case R.id.tv_traverse_depth_by_deque:
                traverseDepthByDeque(getWindow().getDecorView());
                break;
        }
    }
}
