package com.omottec.demoapp.view.recycler;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.omottec.demoapp.app.MyApplication;

import java.util.List;

/**
 * Created by qinbingbing on 10/03/2017.
 */

public class MultiRecyclerAdapter extends RecyclerView.Adapter {
    public static final String TAG = "MultiRecyclerAdapter";
    public static final int VIEW_TYPE_TEXT = 0;
    public static final int VIEW_TYPE_TEXT_IMAGE = 1;
    private List<String> mData;

    public MultiRecyclerAdapter(List<String> data) {
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, String.format("onCreateViewHolder viewType:%d" + viewType));
        switch (viewType) {
            case VIEW_TYPE_TEXT:
                return new SimpleViewHolder(View.inflate(MyApplication.getContext(), android.R.layout.simple_list_item_1, null));
            default:
            case VIEW_TYPE_TEXT_IMAGE:
                return new MultiViewHolder(View.inflate(MyApplication.getContext(), android.R.layout.activity_list_item, null));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, String.format("onBindViewHolder position:%d", position));
        if (holder instanceof SimpleViewHolder) {
            SimpleViewHolder simpleViewHolder = (SimpleViewHolder) holder;
            simpleViewHolder.mTv.setText(mData.get(position));
        } else if (holder instanceof MultiViewHolder) {
            MultiViewHolder multiViewHolder = (MultiViewHolder) holder;
            multiViewHolder.mIv.setImageResource(android.R.drawable.sym_def_app_icon);
            multiViewHolder.mTv.setText(mData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = mData.size();
        Log.d(TAG, String.format("getItemCount itemCount:%d", itemCount));
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        int itemViewType = position % 2 == 0 ? VIEW_TYPE_TEXT : VIEW_TYPE_TEXT_IMAGE;
        Log.d(TAG, String.format("getItemViewType position:%d, itemViewType:%d", position, itemViewType));
        return itemViewType;
    }
}
