package com.omottec.demoapp.view.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by qinbingbing on 11/04/2017.
 */

public abstract class ClickRecyclerAdapter<VH extends ClickViewHolder> extends RecyclerView.Adapter<VH>
        implements OnItemClickListener, OnItemLongClickListener {
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder holder, View view) {
        if (mOnItemClickListener != null)
            mOnItemClickListener.onItemClick(holder, view);
    }

    @Override
    public boolean onItemLongClick(RecyclerView.ViewHolder holder, View view) {
        if (mOnItemLongClickListener != null)
            return mOnItemLongClickListener.onItemLongClick(holder, view);
        return false;
    }
}
