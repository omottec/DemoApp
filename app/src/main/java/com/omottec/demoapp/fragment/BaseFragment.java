package com.omottec.demoapp.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.omottec.demoapp.R;
import com.omottec.demoapp.activity.PageState;

/**
 * Created by qinbingbing on 20/03/2017.
 */

public abstract class BaseFragment extends Fragment {
    protected FragmentActivity mActivity;
    protected FrameLayout mRootFl;
    protected View mContentView;
    protected View mLoadingView;
    protected ValueAnimator mLoadingAnimator;
    protected View mErrorView;
    protected View mEmptyView;
    protected View.OnClickListener mOnClickEmptyOrErrorListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootFl = (FrameLayout) View.inflate(mActivity, R.layout.f_base, null);
        if (mContentView == null) {
            mContentView = createContentView();
            mRootFl.addView(mContentView);
        }
        return mRootFl;
    }

    protected View createLoadingView() {
        return View.inflate(mActivity, R.layout.l_loading, null);
    }

    protected ValueAnimator createLoadingAnimator() {
        ValueAnimator loadingAnimator = ObjectAnimator.ofFloat(mLoadingView.findViewById(R.id.iv_loading),"rotation",0,360);
        loadingAnimator.setDuration(1500);
        loadingAnimator.setRepeatCount(ValueAnimator.INFINITE);
        loadingAnimator.setRepeatMode(ValueAnimator.RESTART);
        loadingAnimator.setInterpolator(new LinearInterpolator());
        return loadingAnimator;
    }


    protected View createErrorView() {
        return View.inflate(mActivity, R.layout.l_net_error, null);
    }

    protected View createEmptyView() {
        return View.inflate(mActivity, R.layout.l_empty, null);
    }

    protected View.OnClickListener createEmptyErrorListener() {
        return null;
    }

    protected abstract View createContentView();

    protected void setState(PageState state) {
        switch (state) {
            case LOADING:
                showLoading();
                break;
            case NORMAL:
                showNormal();
                break;
            case ERROR:
                showError();
                break;
            case EMPTY:
                showEmpty();
                break;
        }
    }

    private void showEmpty() {
        if (mContentView != null) mContentView.setVisibility(View.GONE);
        dismissLoading();
        if (mErrorView != null) mErrorView.setVisibility(View.GONE);
        if (mEmptyView == null) {
            mEmptyView = createEmptyView();
            mRootFl.addView(mEmptyView);
        }
        if (mOnClickEmptyOrErrorListener == null
                && (mOnClickEmptyOrErrorListener = createEmptyErrorListener()) != null)
            mEmptyView.setOnClickListener(mOnClickEmptyOrErrorListener);
        mEmptyView.setVisibility(View.VISIBLE);
    }

    private void showError() {
        if (mContentView != null) mContentView.setVisibility(View.GONE);
        dismissLoading();
        if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
        if (mErrorView == null) {
            mErrorView = createErrorView();
            mRootFl.addView(mErrorView);
        }
        if (mOnClickEmptyOrErrorListener == null
                && (mOnClickEmptyOrErrorListener = createEmptyErrorListener()) != null)
            mEmptyView.setOnClickListener(mOnClickEmptyOrErrorListener);
        mErrorView.setVisibility(View.VISIBLE);
    }

    private void showNormal() {
        if (mContentView != null) mContentView.setVisibility(View.VISIBLE);
        dismissLoading();
        if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
        if (mErrorView != null) mErrorView.setVisibility(View.GONE);
    }

    private void showLoading() {
        if (mContentView != null) mContentView.setVisibility(View.GONE);
        if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
        if (mErrorView != null) mErrorView.setVisibility(View.GONE);
        if (mLoadingView == null) {
            mLoadingView = createLoadingView();
            mRootFl.addView(mLoadingView);
        }
        mLoadingView.setVisibility(View.VISIBLE);
        if (mLoadingAnimator == null)
            mLoadingAnimator = createLoadingAnimator();
        mLoadingAnimator.start();
    }

    private void dismissLoading() {
        if (mLoadingView != null) mLoadingView.setVisibility(View.GONE);
        if (mLoadingAnimator != null) mLoadingAnimator.cancel();
    }

    private boolean isLoading() {
        return mLoadingView != null
                && mLoadingView.getVisibility() == View.VISIBLE
                && mLoadingAnimator != null
                && mLoadingAnimator.isRunning();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isLoading()) dismissLoading();
    }


}
