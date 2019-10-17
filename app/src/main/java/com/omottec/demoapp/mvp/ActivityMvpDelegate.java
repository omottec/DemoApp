package com.omottec.demoapp.mvp;

import android.os.Bundle;

public interface ActivityMvpDelegate<V extends MvpView, P extends MvpPresenter<V>> {
    void onCreate(Bundle bundle);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}