package com.omottec.demoapp.mvp;

import android.os.Bundle;
import androidx.annotation.NonNull;

public class ActivityMvpDelegateImpl<V extends MvpView, P extends MvpPresenter<V>>
        implements ActivityMvpDelegate {
    private MvpDelegateCallback<V, P> mDelegateCallback;

    public ActivityMvpDelegateImpl(@NonNull MvpDelegateCallback<V, P> delegateCallback) {
        if (delegateCallback == null)
            throw new NullPointerException("MvpDelegateCallback is null");
        mDelegateCallback = delegateCallback;
    }

    @Override
    public void onCreate(Bundle bundle) {
        P presenter = mDelegateCallback.createPresenter();
        if (presenter == null)
            throw new NullPointerException("Presenter returned from createPresenter() is null");
        mDelegateCallback.setPresenter(presenter);

        V v = mDelegateCallback.getMvpView();
        if (v == null)
            throw new NullPointerException("View returned from getMvpView() is null");

        getPresenter().attachView(v);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        P p = getPresenter();
        p.detachView();
        p.destroy();
    }

    P getPresenter() {
        P p = mDelegateCallback.getPresenter();
        if (p == null)
            throw new NullPointerException("Presenter returned from getPresenter() is null");
        return p;
    }
}
