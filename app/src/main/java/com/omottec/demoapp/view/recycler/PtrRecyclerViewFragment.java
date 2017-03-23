package com.omottec.demoapp.view.recycler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

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
    private SimpleRecyclerAdapter mSimpleAdapter;
    private PtrRecyclerAdapter mPtrAdapter;
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
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++)
            data.add("item " + i);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mSimpleAdapter = new SimpleRecyclerAdapter(data);
        mPtrAdapter = new PtrRecyclerAdapter(mSimpleAdapter, false, true);
        mSimpleAdapter.setPtrAdapter(mPtrAdapter);
        mRecyclerView.setAdapter(mPtrAdapter);
        mPtrAdapter.notifyDataSetChanged();

        mPtrRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                Log.d(TAG, "onPullDownToRefresh");
                List<String> data = new ArrayList<String>();
                for (int i = 0; i < 5; i++)
                    data.add(0, "item add header " + mHeaderCount);
                mHeaderCount++;
                mPtrRecyclerView.onRefreshComplete();
//                mPtrAdapter.notifyDataSetChanged();
                mSimpleAdapter.addDataAtFirst(data);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                Log.d(TAG, "onPullUpToRefresh");
                if (mFooterCount == 2) {
                    mPtrRecyclerView.onRefreshComplete();
                    mPtrRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    mPtrAdapter.setShowFooter(true);
                    return;
                }
                List<String> data = new ArrayList<String>();
                for (int i = 0; i < 5; i++)
                    data.add("item add footer " + mFooterCount);
                mFooterCount++;
                mPtrRecyclerView.onRefreshComplete();
//                mPtrAdapter.notifyDataSetChanged();
                mSimpleAdapter.addDataAtLast(data);
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

}
