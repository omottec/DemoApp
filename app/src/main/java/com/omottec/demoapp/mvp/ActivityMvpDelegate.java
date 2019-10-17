package com.omottec.demoapp.mvp;

import android.os.Bundle;

/**
 * A delegate for Activities to attach them to mvp
 * @param <V> The type of {@link MvpView}
 * @param <P> The type of {@link MvpPresenter}
 */
public interface ActivityMvpDelegate<V extends MvpView, P extends MvpPresenter<V>> {
    void onCreate(Bundle bundle);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}