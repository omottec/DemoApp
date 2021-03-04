package com.omottec.demoapp.mvp;

import androidx.annotation.UiThread;

/**
 * The root interface for every mvp presenter
 * @param <V>
 */
public interface MvpPresenter<V extends MvpView> {

    /**
     * Set or attach the view to this presenter
     */
    @UiThread
    void attachView(V view);

    /**
     * Will be called if the view has been detached from the Presenter.
     */
    @UiThread
    void detachView();

    /**
     * Will be called if the presenter is no longer needed because the View has been destroyed permanently.
     * This is where you do clean up stuff.
     */
    @UiThread
    void destroy();
}
