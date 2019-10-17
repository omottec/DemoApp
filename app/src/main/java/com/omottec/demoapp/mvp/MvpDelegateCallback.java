package com.omottec.demoapp.mvp;

import android.support.annotation.NonNull;

/**
 *
 * @param <V> The type of {@link MvpView}
 * @param <P> The type of {@link MvpPresenter}
 */
public interface MvpDelegateCallback<V extends MvpView, P extends MvpPresenter<V>> {

    /**
     * Creates the presenter instance
     *
     * @return the created presenter instance
     */
    @NonNull P createPresenter();


    /**
     * Sets the presenter instance
     *
     * @param presenter The presenter instance
     */
    void setPresenter(P presenter);


    /**
     * Gets the presenter
     *
     * @return the presenter instance. can be null.
     */
    P getPresenter();


    /**
     * Gets the MvpView for the presenter
     *
     * @return The view associated with the presenter
     */
    V getMvpView();
}
