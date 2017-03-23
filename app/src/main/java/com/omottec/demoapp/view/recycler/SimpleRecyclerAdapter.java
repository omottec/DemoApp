package com.omottec.demoapp.view.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.omottec.demoapp.R;
import com.omottec.demoapp.app.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinbingbing on 10/03/2017.
 */

public class SimpleRecyclerAdapter extends RecyclerView.Adapter<SimpleViewHolder> {
    public static final String TAG = "SimpleRecyclerAdapter";
    private List<String> mData = new ArrayList<>();
    private PtrRecyclerAdapter mPtrAdapter;

    public SimpleRecyclerAdapter(List<String> data) {
        mData.addAll(data);
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder viewType:" + viewType);
        return new SimpleViewHolder(LayoutInflater.from(MyApplication.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder position:" + position);
        holder.mTv.setText("item " + mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addDataAtLast(List<String> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addDataAtFirst(List<String> data) {
        mData.addAll(0, data);
        notifyDataSetChanged();
    }
}
