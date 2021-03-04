package com.omottec.demoapp.view.recycler;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by qinbingbing on 11/04/2017.
 */

public interface OnItemLongClickListener {
    boolean onItemLongClick(RecyclerView.ViewHolder holder, View view);
}
