package com.omottec.demoapp.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.utils.Logger;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by qinbingbing on 01/02/2018.
 */

public class RxJavaFragment extends Fragment {
    public static final String TAG = "RxJavaFragment";
    private TextView mTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.full_screen_text, container, false);
        mTv = view.findViewById(R.id.tv);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTv.setText(TAG);
        mTv.setOnClickListener(v -> {
//            testRange();
            testZip();
        });
    }

    private void testZip() {
        Logger.d(TAG, "testZip");
        Observable.zip(
                Observable.range(1, 5).doOnCompleted(() -> Logger.d(TAG, "zip range(1, 5).doOnCompleted")),
                Observable.range(6, 5).doOnCompleted(() -> Logger.d(TAG, "zip range(6, 5).doOnCompleted")),
                (a, b) -> a + b)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Logger.d(TAG, "zip onCompleted");
                        Logger.logThread(TAG);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(TAG, "zip onError " + e);
                        Logger.logThread(TAG);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Logger.d(TAG, "zip onNext " + integer);
                        Logger.logThread(TAG);
                    }
                });
    }

    private void testRange() {
        Logger.d(TAG, "testRange");
        Observable.range(6, 5)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Logger.d(TAG, "range onCompleted");
                        Logger.logThread(TAG);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(TAG, "range onError " + e);
                        Logger.logThread(TAG);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Logger.d(TAG, "range onNext " + integer);
                        Logger.logThread(TAG);
                    }
                });
    }
}
