package ru.moleculus.moveme.net.beans.moveme;

import ru.moleculus.moveme.net.beans.BaseResponse;

/**
 * Created by Oleg on 11.02.2016.
 */
public class BaseMoveMeResponse extends BaseResponse{

    private boolean success;
    private String message;
    private long id;

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public long getId() {
        return id;
    }
}
