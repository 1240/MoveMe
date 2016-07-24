package ru.moleculus.moveme.presenters;

import ru.moleculus.moveme.net.InternalServerException;

/**
 * Created by Oleg on 02.03.2016.
 */
public interface LoginViewPresenter extends BasePresenter {

    void requestToken(String smsCode);

    void sendNumber(String number);

    void sendNumber();

    void registerUser(int typeOfAccount, String firstName,
                      String lastName, String middleName, String mail);
}
