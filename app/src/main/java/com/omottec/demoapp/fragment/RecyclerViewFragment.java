package com.omottec.demoapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.omottec.demoapp.R;
import com.omottec.demoapp.view.recycler.SimpleRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinbingbing on 10/03/2017.
 */

public class RecyclerViewFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.f_recycler_view, null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 200; i++)
            list.add("item " + i);
        mRecyclerView.setAdapter(new SimpleRecyclerAdapter(mContext, list));
        return mRecyclerView;
    }
}
