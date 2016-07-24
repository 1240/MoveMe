package ru.moleculus.moveme.interactors;

import ru.moleculus.moveme.callbacks.BaseRequestCallback;
import ru.moleculus.moveme.net.beans.BaseResponse;
import rx.Observable;

/**
 * Created by Oleg on 02.03.2016.
 */
public interface RequestInteractor<T extends BaseResponse> {

    void makeRequest(Observable<T> observable, BaseRequestCallback<T> callback);
}
