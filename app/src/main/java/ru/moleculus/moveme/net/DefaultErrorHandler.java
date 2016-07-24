package ru.moleculus.moveme.net;

import ru.moleculus.moveme.callbacks.BaseRequestCallback;
import ru.moleculus.moveme.net.beans.BaseResponse;
import rx.Observer;

/**
 * Created by Oleg on 25.02.2016.
 */
public class DefaultErrorHandler<T extends BaseResponse> implements Observer<T> {

    private BaseRequestCallback<T> callback;

    public DefaultErrorHandler(BaseRequestCallback<T> callback) {
        this.callback = callback;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        callback.onRequestError(e.getLocalizedMessage());
        e.printStackTrace();
    }

    @Override
    public void onNext(T t) {
        if (t.isSuccess()) {
            callback.onRequestSuccess(t);
        } else {
            callback.onRequestError(t.getMessage());
        }
    }


}
