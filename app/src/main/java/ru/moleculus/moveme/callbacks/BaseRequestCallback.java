package ru.moleculus.moveme.callbacks;

import ru.moleculus.moveme.net.beans.BaseResponse;

/**
 * Created by Oleg on 12.02.2016.
 */
public interface BaseRequestCallback<T extends BaseResponse> {

    void onRequestError(String message);

    void onRequestSuccess(T t);
}
