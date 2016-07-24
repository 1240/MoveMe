package ru.moleculus.moveme.view;

import java.io.File;

import ru.moleculus.moveme.data.beans.User;

/**
 * Created by Oleg on 02.03.2016.
 */
public interface UserInfoView extends BaseView{

    void showUserData(User user);

    void notifyDataUpdated();

    void notifyIdUpdated(long id);

    void sendPassportData();

    void sendCardData();

    void sendUserPhotoFile(File file);

    void sendUserFile(File file);

    void onFileSent(String url);
}
