package com.omottec.demoapp.view.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewParent;

/**
 * Created by qinbingbing on 11/04/2017.
 */

public interface OnItemLongClickListener {
    boolean onItemLongClick(RecyclerView.ViewHolder holder, View view);
}
