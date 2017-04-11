package com.omottec.demoapp.view.recycler;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewParent;
import android.widget.AdapterView;

/**
 * Created by qinbingbing on 11/04/2017.
 */

public interface OnItemClickListener {
    void onItemClick(RecyclerView.ViewHolder holder, View view);
}
