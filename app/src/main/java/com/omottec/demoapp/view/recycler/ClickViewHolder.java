package com.omottec.demoapp.view.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by qinbingbing on 11/04/2017.
 */

public class ClickViewHolder extends RecyclerView.ViewHolder {

    public ClickViewHolder(View itemView, OnItemClickListener onItemClickListener, OnItemLongClickListener onItemLongClickListener) {
        super(itemView);
        if (onItemClickListener != null)
            itemView.setOnClickListener(v -> {
                onItemClickListener.onItemClick(ClickViewHolder.this, v);
            });
        if (onItemLongClickListener != null)
            itemView.setOnLongClickListener(v -> {
                return onItemLongClickListener.onItemLongClick(ClickViewHolder.this, v);
            });
    }
}
