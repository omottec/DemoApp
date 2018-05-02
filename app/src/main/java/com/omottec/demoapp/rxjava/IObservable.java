package com.omottec.demoapp.rxjava;

/**
 * Created by qinbingbing on 02/05/2018.
 */

public interface IObservable<T> {
    IDisposable subscribe(IObserver<T> observer);
}
