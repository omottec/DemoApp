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
import com.omottec.demoapp.utils.TouchUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinbingbing on 22/03/2017.
 */

public class PtrRecyclerViewFragment extends BaseFragment {
    public static final String TAG = "PtrRecyclerViewFragment";
    private PtrRecyclerView mPtrRecyclerView;
    private RecyclerView mRecyclerView;
    private List<String> mData = new ArrayList<>();
    private SimpleRecyclerAdapter mSimpleAdapter;
    private PtrRecyclerAdapter mPtrAdapter;
    private int mHeaderCount;
    private int mFooterCount;

    @Override
    protected View createContentView() {
        View view = View.inflate(mActivity, R.layout.f_ptr_recycler_view, null);
        mPtrRecyclerView = (PtrRecyclerView) view.findViewById(R.id.ptr_recycler_view);
        mPtrRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        mRecyclerView = mPtrRecyclerView.getRefreshableView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 200; i++)
            data.add("item " + i);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mSimpleAdapter = new SimpleRecyclerAdapter(data);
        mPtrAdapter = new PtrRecyclerAdapter(mPtrRecyclerView, mSimpleAdapter, false, true);
        mRecyclerView.setAdapter(mPtrAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                Log.d(TAG, "onScrollStateChanged " + TouchUtils.getRecyclerScrollState(newState)
                        + ", isReadyForPullStart:" + mPtrRecyclerView.isReadyForPullStart());
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.d(TAG, "onScrolled isReadyForPullStart:" + mPtrRecyclerView.isReadyForPullStart());
            }
        });
        mPtrAdapter.notifyDataSetChanged();

        mPtrRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                Log.d(TAG, "onPullDownToRefresh");
                mPtrAdapter.setShowFooter(false);
                List<String> data = new ArrayList<String>();
                for (int i = 0; i < 5; i++)
                    data.add(0, "item add header " + mHeaderCount);
                mHeaderCount++;
                mPtrRecyclerView.onRefreshComplete();
                mSimpleAdapter.addDataAtFirst(data);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                Log.d(TAG, "onPullUpToRefresh");
                if (mFooterCount == 2) {
                    mPtrRecyclerView.onRefreshComplete();
                    mPtrAdapter.setShowFooter(true);
                    return;
                }
                List<String> data = new ArrayList<String>();
                for (int i = 0; i < 20; i++)
                    data.add("item add footer " + mFooterCount);
                mFooterCount++;
                mPtrRecyclerView.onRefreshComplete();
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
