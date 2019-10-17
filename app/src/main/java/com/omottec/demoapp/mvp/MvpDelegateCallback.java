package com.omottec.demoapp.mvp;

import android.support.annotation.NonNull;

public interface MvpDelegateCallback<V extends MvpView, P extends MvpPresenter<V>> {
    @NonNull P createPresenter();

    void setPresenter(P presenter);

    P getPresenter();

    V getMvpView();
}
