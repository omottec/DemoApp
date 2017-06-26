package com.omottec.demoapp.view.frame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.view.recycler.SimpleRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinbingbing on 26/06/2017.
 */

public class MajorDetailFragment extends Fragment {
    private View mRootView;
    private RecyclerView mMajorRv;
    private ListView mMajorLv;
    private RecyclerView mDetailRv;
    private SimpleRecyclerAdapter mMajorAdapter;
    private SimpleRecyclerAdapter mDetailAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.f_major_detail, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mMajorLv = (ListView) mRootView.findViewById(R.id.lv_major);
        mMajorRv = (RecyclerView) mRootView.findViewById(R.id.rv_major);
        mDetailRv = (RecyclerView) mRootView.findViewById(R.id.rv_detail);
        List<String> majorList = new ArrayList<>();
        for (int i = 0; i < 100; i++)
            majorList.add("major " + i);
        mMajorAdapter = new SimpleRecyclerAdapter(majorList);
        mMajorAdapter.setOnItemClickListener((holder, view1) -> {
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < 200; i++)
                list.add("major " + holder.getAdapterPosition() + " detail " + i);
            mDetailAdapter.reset(list);
        });
        mMajorRv.setAdapter(mMajorAdapter);
        mMajorRv.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mMajorLv.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, majorList));
        List<String> detailList = new ArrayList<>();
        for (int i = 0; i < 200; i++)
            detailList.add("major 0 detail " + i);
        mDetailRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDetailAdapter = new SimpleRecyclerAdapter(detailList);
        mDetailRv.setAdapter(mDetailAdapter);
    }
}
