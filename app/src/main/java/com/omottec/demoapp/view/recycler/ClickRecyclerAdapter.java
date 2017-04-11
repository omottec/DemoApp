package com.omottec.demoapp.view.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewParent;

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
    public void onItemClick(ViewParent parent, View view, int adapterPosition, int layoutPosition) {
        if (mOnItemClickListener != null)
            mOnItemClickListener.onItemClick(parent, view, adapterPosition, layoutPosition);
    }

    @Override
    public boolean onItemLongClick(ViewParent parent, View view, int adapterPosition, int layoutPosition) {
        if (mOnItemLongClickListener != null)
            return mOnItemLongClickListener.onItemLongClick(parent, view, adapterPosition, layoutPosition);
        return false;
    }
}
