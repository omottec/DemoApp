package com.omottec.demoapp.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 14/03/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected static final int STATE_LOADING = 0;
    protected static final int STATE_NORMAL = 1;
    protected static final int STATE_EMPTY = 2;
    protected static final int STATE_ERROR = 3;
    protected View mRootView;
    protected Toolbar mToolbar;
    protected View mToolbarLeft;
    protected View mToolbarMid;
    protected View mToolbarRight;
    protected FrameLayout mContainer;
    protected View mContentView;
    protected View mLoadingView;
    protected ValueAnimator mLoadingAnimator;
    protected View mErrorView;
    protected View mEmptyView;
    protected View.OnClickListener mOnClickEmptyOrErrorListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = View.inflate(this, R.layout.a_base, null);
        mToolbar = (Toolbar) mRootView.findViewById(R.id.tb);
        mToolbarLeft = createToolBarLeft();
        mToolbarMid = createToolBarMid(R.string.app_name);
        mToolbarRight = createToolBarRight();
        if (mToolbarLeft != null) mToolbar.addView(mToolbarLeft);
        if (mToolbarMid != null) mToolbar.addView(mToolbarMid);
        if (mToolbarRight != null) mToolbar.addView(mToolbarRight);
        mContainer = (FrameLayout) mRootView.findViewById(R.id.container);
        mContentView = createContentView();
        mContainer.addView(mContentView);
        setContentView(mRootView);
    }

    protected View createToolBarLeft() {
        ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.iv_toolbar_left);
        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics());
        Toolbar.LayoutParams lp = new Toolbar.LayoutParams(size, size);
        lp.gravity = Gravity.LEFT;
        iv.setLayoutParams(lp);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        return iv;
    }

    protected View createToolBarMid(int titleResId) {
        TextView tv = new TextView(this);
        tv.setText(titleResId);
        Toolbar.LayoutParams lp = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        return tv;
    }

    protected View createToolBarRight() {
        return null;
    }

    protected View createLoadingView() {
        mLoadingView = View.inflate(this, R.layout.l_loading, null);
        return mLoadingView;
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
        mErrorView = View.inflate(this, R.layout.l_net_error, null);
        if (mOnClickEmptyOrErrorListener != null)
            mErrorView.setOnClickListener(mOnClickEmptyOrErrorListener);
        return mErrorView;
    }

    protected View createEmptyView() {
        mEmptyView = View.inflate(this, R.layout.l_empty, null);
        if (mOnClickEmptyOrErrorListener != null)
            mEmptyView.setOnClickListener(mOnClickEmptyOrErrorListener);
        return mEmptyView;
    }

    protected abstract View createContentView();

    protected void setOnClickEmptyOrErrorListener(View.OnClickListener listener) {
        mOnClickEmptyOrErrorListener = listener;
    }

    protected void setState(int state) {
        switch (state) {
            case STATE_LOADING:
                showLoading();
                break;
            case STATE_NORMAL:
                showNormal();
                break;
            case STATE_ERROR:
                showError();
                break;
            case STATE_EMPTY:
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
            mContainer.addView(mEmptyView);
        }
        mEmptyView.setVisibility(View.VISIBLE);
    }

    private void showError() {
        if (mContentView != null) mContentView.setVisibility(View.GONE);
        dismissLoading();
        if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
        if (mErrorView == null) {
            mErrorView = createErrorView();
            mContainer.addView(mErrorView);
        }
        mErrorView.setVisibility(View.VISIBLE);
    }

    private void showNormal() {
        if (mContentView != null) mContentView.setVisibility(View.VISIBLE);
        dismissLoading();
        if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
        if (mErrorView != null) mErrorView.setVisibility(View.GONE);
    }

    private void showLoading() {
        if (mContentView != null) mContentView.setVisibility(View.VISIBLE);
        if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
        if (mErrorView != null) mErrorView.setVisibility(View.GONE);
        if (null == mLoadingView) {
            mLoadingView = createLoadingView();
            mContainer.addView(mLoadingView);
        }
        mLoadingView.setVisibility(View.VISIBLE);
        if (mLoadingAnimator == null) mLoadingAnimator = createLoadingAnimator();
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
    public void onBackPressed() {
        if (isLoading()) {
            dismissLoading();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isLoading()) dismissLoading();
    }
}
