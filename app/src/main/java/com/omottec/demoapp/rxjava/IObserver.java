package com.omottec.demoapp.rxjava;

/**
 * Created by qinbingbing on 02/05/2018.
 */

public interface IObserver<T> {
    void onNext(T t);
    void onError(Throwable e);
    void onCompleted();
}
