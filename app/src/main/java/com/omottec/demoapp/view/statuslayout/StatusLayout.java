package com.omottec.demoapp.view.statuslayout;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by qinbingbing on 30/06/2017.
 */

public class StatusLayout extends FrameLayout {
    public static final int VIEW_TYPE_LOADING = 1;
    public static final int VIEW_TYPE_CONTENT = 2;
    public static final int VIEW_TYPE_NET_ERR = 3;
    public static final int VIEW_TYPE_EMPTY = 4;

    private SparseArray<View> mViewArray = new SparseArray<>();
    private StatusConfiguration mConfiguration;


    public StatusLayout(@NonNull Context context) {
        super(context);
    }

    public StatusLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StatusLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(StatusConfiguration configuration) {
        mConfiguration = configuration;
        if (mConfiguration.mLoadingResId != 0)
            addViewById(mConfiguration.mLoadingResId, VIEW_TYPE_LOADING);
        if (mConfiguration.mContentResId != 0)
            addViewById(mConfiguration.mContentResId, VIEW_TYPE_CONTENT);
        if (mConfiguration.mNetErrVs != null)
            addView(mConfiguration.mNetErrVs);
        if (mConfiguration.mEmptyVs != null)
            addView(mConfiguration.mEmptyVs);
    }

    private void addViewById(int resId, int viewType) {
        View view = LayoutInflater.from(mConfiguration.mContext).inflate(resId, null);
        mViewArray.put(viewType, view);
        addView(view);
    }

    private void showViewByType(int viewType) {
        for (int i = 0; i < mViewArray.size(); i++) {
            int type = mViewArray.keyAt(i);
            View view = mViewArray.valueAt(i);
            view.setVisibility(type == viewType ? View.VISIBLE : View.GONE);
        }
    }

    public void showLoading() {
        if (mViewArray.get(VIEW_TYPE_LOADING) != null)
            showViewByType(VIEW_TYPE_LOADING);
    }

    public void showContent() {
        if (mViewArray.get(VIEW_TYPE_CONTENT) != null)
            showViewByType(VIEW_TYPE_CONTENT);
    }

    public void showEmpty() {
        if (inflateLayout(VIEW_TYPE_EMPTY))
            showViewByType(VIEW_TYPE_EMPTY);
    }

    public void showNetErr() {
        if (inflateLayout(VIEW_TYPE_NET_ERR))
            showViewByType(VIEW_TYPE_NET_ERR);
    }

    private boolean inflateLayout(int viewType) {
        boolean inflated = mViewArray.get(viewType) != null;
        if (inflated) return inflated;
        switch (viewType) {
            case VIEW_TYPE_NET_ERR:
                if (mConfiguration.mNetErrVs != null) {
                    View view = mConfiguration.mNetErrVs.inflate();
                    setRetryListener(view);
                    mViewArray.put(viewType, view);
                    inflated = true;
                } else {
                    inflated = false;
                }
                break;
            case VIEW_TYPE_EMPTY:
                if (mConfiguration.mEmptyVs != null) {
                    View view = mConfiguration.mEmptyVs.inflate();
                    setRetryListener(view);
                    mViewArray.put(viewType, view);
                    inflated = true;
                } else {
                    inflated = false;
                }
                break;
        }
        return inflated;
    }

    public void setRetryListener(View v) {
        if (v != null && mConfiguration.mOnRetryListener != null)
            v.setOnClickListener(view -> mConfiguration.mOnRetryListener.onRetry(view));
    }
}
