package com.omottec.demoapp.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omottec.demoapp.R;

import com.omottec.logger.Logger;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
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
//            testZip();
//            testJust();
            testPolling();
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

    private void testJust() {
        Logger.d(TAG, "testJust");
        Observable.just("Hello word")
                .map((Func1<String, Object>) s -> s.length())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Logger.d(TAG, "get " + s + " @ " + Thread.currentThread().getName());
                });
    }

    private void testPolling() {
        Log.d(TAG, "testPolling");
        pollingServer(1)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "onError", throwable);
                    }
                });

    }

    private Observable<Integer> pollingServer(int delay) {
        return Observable/*.just(1)*/
                .create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        Log.d(TAG, "OnSubscribe call");
//                        subscriber.onNext(1);
                        throw new IllegalStateException("must error");
                    }
                })
                .onErrorReturn(new Func1<Throwable, Integer>() {
                    @Override
                    public Integer call(Throwable throwable) {
                        Log.d(TAG, "onErrorReturn call");
                        return 1;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .delaySubscription(delay, TimeUnit.SECONDS)
                .switchMap(new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Integer integer) {
                        Log.d(TAG, "switchMap call");
                        return pollingServer(delay);
                    }
                });
    }


}
