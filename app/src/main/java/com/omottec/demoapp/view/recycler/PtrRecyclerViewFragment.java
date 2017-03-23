package com.omottec.demoapp.view.recycler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.omottec.demoapp.R;
import com.omottec.demoapp.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinbingbing on 22/03/2017.
 */

public class PtrRecyclerViewFragment extends BaseFragment {
    public static final String TAG = "PtrRecyclerViewFragment";
    private PullToRefreshRecyclerView mPtrRecyclerView;
    private RecyclerView mRecyclerView;
    private List<String> mData = new ArrayList<>();
    private PtrRecyclerViewAdapter mAdapter;
    private int mHeaderCount;
    private int mFooterCount;

    @Override
    protected View createContentView() {
        View view = View.inflate(mActivity, R.layout.f_ptr_recycler_view, null);
        mPtrRecyclerView = (PullToRefreshRecyclerView) view.findViewById(R.id.ptr_recycler_view);
        mPtrRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        mRecyclerView = mPtrRecyclerView.getRefreshableView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (int i = 0; i < 100; i++)
            mData.add("item " + i);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new PtrRecyclerViewAdapter(new SimpleAdapter(), true, true);
        mAdapter.setShowHeader(true);
        mRecyclerView.setAdapter(mAdapter);

        mPtrRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                Log.d(TAG, "onPullDownToRefresh");
                for (int i = 0; i < 5; i++)
                    mData.add(0, "item add header " + mHeaderCount);
                mHeaderCount++;
                mPtrRecyclerView.onRefreshComplete();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                Log.d(TAG, "onPullUpToRefresh");
                if (mFooterCount == 2) {
                    mPtrRecyclerView.onRefreshComplete();
                    mPtrRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    mAdapter.setShowFooter(true);
                    return;
                }
                for (int i = 0; i < 5; i++)
                    mData.add("item add footer " + mFooterCount);
                mFooterCount++;
                mPtrRecyclerView.onRefreshComplete();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class SimpleAdapter extends RecyclerView.Adapter<SimpleViewHolder> {


        @Override
        public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SimpleViewHolder(LayoutInflater.from(mActivity).inflate(android.R.layout.simple_list_item_1, parent, false));
        }

        @Override
        public void onBindViewHolder(SimpleViewHolder holder, int position) {
            holder.mTv.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }


}
