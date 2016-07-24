package ru.moleculus.moveme.net.beans.googlemaps;

import ru.moleculus.moveme.net.beans.BaseResponse;

/**
 * Created by Oleg on 02.04.2016.
 */
public class BaseMapsResponse extends BaseResponse {

    private String error_message;

    @Override
    public boolean isSuccess() {
        return error_message == null;
    }

    @Override
    public String getMessage() {
        return error_message;
    }
}
