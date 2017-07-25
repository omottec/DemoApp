package com.omottec.demoapp.view.recycler;

import android.os.Bundle;
import android.os.SystemClock;
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
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qinbingbing on 22/03/2017.
 */

public class PtrPicRecyclerFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener2<RecyclerView> {
    public static final String TAG = "PtrPicRecyclerFragment";
    public static final int PAGE_SIZE = 30;
    private PtrRecyclerView mPtrRecyclerView;
    private RecyclerView mRecyclerView;
    private List<PicRecyclerAdapter.PicItem> mData = new ArrayList<>();
    private PtrRecyclerAdapter mPtrAdapter;
    private PicRecyclerAdapter mPicAdapter;
    private volatile int mOffset;
    private int mTotal = PAGE_SIZE * 30;

    @Override
    protected View createContentView() {
        View view = View.inflate(mActivity, R.layout.f_ptr_recycler_view, null);
        mPtrRecyclerView = view.findViewById(R.id.ptr_recycler_view);
        mPtrRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        mRecyclerView = mPtrRecyclerView.getRefreshableView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mPicAdapter = new PicRecyclerAdapter();
        mPtrAdapter = new PtrRecyclerAdapter(mPtrRecyclerView, mPicAdapter, false, true);
        mRecyclerView.setAdapter(mPtrAdapter);
        mPtrRecyclerView._setOnRefreshListener(this);
//        mPtrRecyclerView.setOnRefreshListener(this);
        mPtrRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        loadData();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void loadData() {
        List<PicRecyclerAdapter.PicItem> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            String url = "http://lorempixel.com/400/";
            url += (370 + new Random().nextInt(30));
            data.add(new PicRecyclerAdapter.PicItem(url, "Item " + (mOffset + i)));
        }
        Observable.just(data)
                .delay(100 + new Random().nextInt(100), TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<PicRecyclerAdapter.PicItem>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError");
                    }

                    @Override
                    public void onNext(List<PicRecyclerAdapter.PicItem> picItems) {
                        Log.d(TAG, "onNext");
                        if (mOffset == 0) {
                            mPicAdapter.reset(picItems);
                        } else {
                            mPicAdapter.addDataAtLast(picItems);
                        }
                        mOffset += PAGE_SIZE;
                        mPtrRecyclerView._onRefreshComplete();
//                        mPtrRecyclerView.onRefreshComplete();
                        if (mOffset >= mTotal) {
                            mPtrAdapter.setShowFooter(true);
                        }
                    }
                });
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
        Log.d(TAG, "onPullDownToRefresh");
        mPtrAdapter.setShowFooter(false);
        mOffset = 0;
        loadData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
        Log.d(TAG, "onPullUpToRefresh");
        loadData();
    }
}
