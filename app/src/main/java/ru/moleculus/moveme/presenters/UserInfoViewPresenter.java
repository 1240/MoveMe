package ru.moleculus.moveme.presenters;

import java.io.File;

import ru.moleculus.moveme.data.beans.Passport;
import ru.moleculus.moveme.data.beans.PaymentMethod;
import ru.moleculus.moveme.data.beans.User;
import ru.moleculus.moveme.net.InternalServerException;

/**
 * Created by Oleg on 02.03.2016.
 */
public interface UserInfoViewPresenter extends BasePresenter{

    void requestUser();

    void updateUser(User user);

    void addPassportData(Passport passport);

    void addCardData(PaymentMethod method);

    void loadUserPhoto(File file);

    void loadUserFile(File file);

    void updateUserPrice(User user);
}
