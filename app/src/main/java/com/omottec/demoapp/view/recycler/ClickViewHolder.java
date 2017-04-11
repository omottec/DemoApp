package com.omottec.demoapp.view.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.omottec.demoapp.view.recycler.OnItemClickListener;
import com.omottec.demoapp.view.recycler.OnItemLongClickListener;

/**
 * Created by qinbingbing on 11/04/2017.
 */

public class ClickViewHolder extends RecyclerView.ViewHolder {

    public ClickViewHolder(View itemView, OnItemClickListener onItemClickListener, OnItemLongClickListener onItemLongClickListener) {
        super(itemView);
        itemView.setOnClickListener(v -> {
            onItemClickListener.onItemClick(v.getParent(), v, getAdapterPosition(), getLayoutPosition());
        });
        itemView.setOnLongClickListener(v -> {
            return onItemLongClickListener.onItemLongClick(v.getParent(), v, getAdapterPosition(), getLayoutPosition());
        });
    }
}
