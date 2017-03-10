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

import com.omottec.demoapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinbingbing on 10/03/2017.
 */

public class RecyclerViewFragment extends Fragment {
    public static final String TAG = "RecyclerViewFragment";
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
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(new NormalRecyclerViewAdapter(mContext));
        return mRecyclerView;
    }

    private class NormalRecyclerViewAdapter extends Adapter<NormalRecyclerViewAdapter.NormalViewHolder> {
        private List<String> mData = new ArrayList<>();
        private Context mContext;

        public NormalRecyclerViewAdapter(Context context) {
            mContext = context;
            for (int i = 0; i < 200; i++)
                mData.add("item " + i);
        }

        @Override
        public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.d(TAG, "onCreateViewHolder parent:" + parent + ", viewType:" + viewType);
            return new NormalViewHolder(View.inflate(mContext, android.R.layout.simple_list_item_1, null));
        }

        @Override
        public void onBindViewHolder(NormalViewHolder holder, int position) {
            Log.d(TAG, "onBindViewHolder holder:" + holder + ", position:" + position);
            holder.mTv.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class NormalViewHolder extends RecyclerView.ViewHolder {
            TextView mTv;

            public NormalViewHolder(View itemView) {
                super(itemView);
                mTv = (TextView) itemView.findViewById(android.R.id.text1);
            }
        }
    }
}
