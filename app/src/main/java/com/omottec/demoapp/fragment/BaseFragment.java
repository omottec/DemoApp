package com.omottec.demoapp.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.omottec.demoapp.R;
import com.omottec.demoapp.activity.PageState;
import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.components.FragmentLifecycleProvider;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by qinbingbing on 20/03/2017.
 */

public abstract class BaseFragment extends Fragment implements FragmentLifecycleProvider {
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
        mLifecycleSubject.onNext(FragmentEvent.CREATE);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootFl != null) return mRootFl;
        mRootFl = (FrameLayout) View.inflate(mActivity, R.layout.f_base, null);
        mContentView = createContentView();
        mRootFl.addView(mContentView);
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

    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();

    @Override
    public Observable<FragmentEvent> lifecycle() {
        return mLifecycleSubject.asObservable();
    }

    @Override
    public <T> Observable.Transformer<? super T, ? extends T> bindUntilEvent(FragmentEvent event) {
        return RxLifecycle.bindUntilFragmentEvent(mLifecycleSubject, event);
    }

    @Override
    public <T> Observable.Transformer<? super T, ? extends T> bindToLifecycle() {
        return RxLifecycle.bindFragment(mLifecycleSubject);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
    }

    @Override
    public void onStart() {
        super.onStart();
        mLifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    public void onResume() {
        super.onResume();
        mLifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        mLifecycleSubject.onNext(FragmentEvent.PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        mLifecycleSubject.onNext(FragmentEvent.STOP);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLifecycleSubject.onNext(FragmentEvent.DESTROY);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mLifecycleSubject.onNext(FragmentEvent.DETACH);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        onUserVisibleHint(isVisibleToUser);
    }

    /**
     * 用于ViewPager切换Fragment的时候，提示当前可见还是隐藏
     * @param isVisibleToUser 是否对用户可见
     */
    protected void onUserVisibleHint(boolean isVisibleToUser) {
    }
}
