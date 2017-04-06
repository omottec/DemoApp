package com.omottec.demoapp.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.fragment.AnimatorFragment;
import com.omottec.demoapp.fragment.BaseFragment;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by qinbingbing on 27/03/2017.
 */

public class RxJavaFragment extends BaseFragment {
    public static final String TAG = "RxJavaFragment";

    private TextView mTv;

    private Observer<String> mObserver = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.d(TAG, "Observer onSubscribe");
        }

        @Override
        public void onNext(String s) {
            Log.d(TAG, "Observer onNext " + s);
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "Observer onError");
        }

        @Override
        public void onComplete() {
            Log.d(TAG, "Observer onComplete");
        }
    };

    private Subscriber<String> mSubscriber = new Subscriber<String>() {
        @Override
        public void onSubscribe(Subscription s) {
            Log.d(TAG, "Subscriber onSubscribe");
        }

        @Override
        public void onNext(String s) {
            Log.d(TAG, "Subscriber onNext");
        }

        @Override
        public void onError(Throwable t) {
            Log.d(TAG, "Subscriber onError");
        }

        @Override
        public void onComplete() {
            Log.d(TAG, "Subscriber onComplete");
        }
    };

    @Override
    protected View createContentView() {
        View view = View.inflate(mActivity, R.layout.full_screen_text, null);
        mTv = (TextView) view.findViewById(R.id.tv);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTv.setText(TAG);
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.d(TAG, "ObservableOnSubscribe#subscribe");
                Log.d(TAG, "ObservableEmitter#onNext Hello");
                e.onNext("Hello");
                Log.d(TAG, "ObservableEmitter#onNext Daniel");
                e.onNext("Daniel");
                Log.d(TAG, "ObservableEmitter#onNext Bond");
                e.onNext("Bond");
                Log.d(TAG, "ObservableEmitter#onComplete");
                e.onComplete();
            }
        });
        observable.subscribe(mObserver);

//        Flowable.just("Hello world").subscribe(System.out::println);

        /*Flowable.fromCallable(() -> {
            Thread.sleep(1000);
            return "Done";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(System.out::println, Throwable::printStackTrace);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        /*Flowable.range(1, 10)
                .observeOn(Schedulers.computation())
                .map(v -> v * v)
                .blockingSubscribe(System.out::println);*/

        /*Flowable.range(1, 10)
                .flatMap(v ->
                        Flowable.just(v)
                                .subscribeOn(Schedulers.computation())
                                .map(w -> w * w))
                .blockingSubscribe(System.out::println);*/
    }
}
