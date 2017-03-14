package com.omottec.demoapp.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 14/03/2017.
 */

public abstract class BaseActivity extends FragmentActivity {
    protected static final int LOADING = 0;
    protected static final int FINISH = 1;
    protected static final int EMPTY = 2;
    protected static final int ERROR = 3;
    private View mRootView;
    private Toolbar mToolbar;
    private FrameLayout mContainer;
    private View mContentView;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mRootView = View.inflate(this, R.layout.a_base, null);
        mToolbar = (Toolbar) mRootView.findViewById(R.id.tb);
        mContainer = (FrameLayout) mRootView.findViewById(R.id.container);
    }

    protected View createLoadingView() {
        return null;
    }

    protected View createErrorView() {
        return null;
    }

    protected View createEmptyView() {
        return null;
    }

    protected View createContentView() {
        return null;
    }

    protected void setState(int state) {
        switch (state) {
            case LOADING:
                showLoading();
                break;
            case FINISH:
                showFinish();
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

    }

    private void showError() {
    }

    private void showFinish() {
    }

    private void showLoading() {

    }
}
