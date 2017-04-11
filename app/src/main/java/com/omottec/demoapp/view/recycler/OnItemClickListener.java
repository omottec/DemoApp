package com.omottec.demoapp.view.recycler;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewParent;
import android.widget.AdapterView;

/**
 * Created by qinbingbing on 11/04/2017.
 */

public interface OnItemClickListener {
    void onItemClick(ViewParent parent, View view, int adapterPosition, int layoutPosition);
}
