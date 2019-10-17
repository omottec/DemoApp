package com.omottec.demoapp.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

public class FragmentMvpDelegateImpl<V extends MvpView, P extends MvpPresenter<V>>
        implements FragmentMvpDelegate<V, P> {
    private MvpDelegateCallback<V, P> mDelegateCallback;

    public FragmentMvpDelegateImpl(@NonNull MvpDelegateCallback<V, P> delegateCallback) {
        if (delegateCallback == null)
            throw new NullPointerException("MvpDelegateCallback is null!");
        mDelegateCallback = delegateCallback;
    }

    @Override
    public void onAttach(Context context) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        P presenter = mDelegateCallback.createPresenter();
        if (presenter == null)
            throw new NullPointerException("Presenter returned from createPresenter() is null");
        mDelegateCallback.setPresenter(presenter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        V v = mDelegateCallback.getMvpView();
        if (v == null)
            throw new NullPointerException("View returned from getMvpView() is null");

        getPresenter().attachView(v);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

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
    public void onDestroyView() {
        getPresenter().detachView();
    }

    @Override
    public void onDestroy() {
        getPresenter().destroy();
    }

    @Override
    public void onDetach() {

    }

    P getPresenter() {
        P p = mDelegateCallback.getPresenter();
        if (p == null)
            throw new NullPointerException("Presenter returned from getPresenter() is null");
        return p;
    }
}
