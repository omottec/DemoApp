package com.omottec.demoapp.mvp;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;

/**
 * A delegate for Fragments to attach them to mvp
 * @param <V> The type of {@link MvpView}
 * @param <P> The type of {@link MvpPresenter}
 */
public interface FragmentMvpDelegate<V extends MvpView, P extends MvpPresenter<V>> {
    void onAttach(Context context);

    void onCreate(Bundle savedInstanceState);

    void onViewCreated(View view, @Nullable Bundle savedInstanceState);

    void onActivityCreated(@Nullable Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroyView();

    void onDestroy();

    void onDetach();
}
