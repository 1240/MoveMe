package ru.moleculus.moveme.net.beans.moveme;

import ru.moleculus.moveme.data.beans.User;

/**
 * Created by Oleg on 02.03.2016.
 */
public class UserResponse extends BaseMoveMeResponse {

    private User user;

    public User getUser(){
        return user;
    }
}
