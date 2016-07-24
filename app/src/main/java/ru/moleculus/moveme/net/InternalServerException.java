package ru.moleculus.moveme.net;

import retrofit.RetrofitError;

/**
 * Created by Oleg on 10.03.2016.
 */
public class InternalServerException extends Exception {

    private RetrofitError error;

    public InternalServerException(RetrofitError error) {
        this.error = error;
    }

    @Override
    public String getLocalizedMessage() {
        return error.getLocalizedMessage();
    }
}
