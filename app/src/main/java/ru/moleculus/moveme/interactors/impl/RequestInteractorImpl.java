package ru.moleculus.moveme.interactors.impl;

import ru.moleculus.moveme.callbacks.BaseRequestCallback;
import ru.moleculus.moveme.interactors.RequestInteractor;
import ru.moleculus.moveme.net.DefaultErrorHandler;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Oleg on 02.03.2016.
 */
public class RequestInteractorImpl implements RequestInteractor{
    @Override
    public void makeRequest(Observable observable, BaseRequestCallback callback) {
        observable.subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(
                new DefaultErrorHandler<>(callback));
    }
}
