package com.omottec.demoapp.view.recycler;

import android.view.View;
import android.view.ViewParent;

/**
 * Created by qinbingbing on 11/04/2017.
 */

public interface OnItemLongClickListener {
    boolean onItemLongClick(ViewParent parent, View view, int adapterPosition, int layoutPosition);
}
