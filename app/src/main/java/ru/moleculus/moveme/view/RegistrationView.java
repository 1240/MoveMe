package ru.moleculus.moveme.view;

/**
 * Created by Oleg on 29.02.2016.
 */
public interface RegistrationView extends BaseView {

    void onRegistrationFinish();

    boolean isDataValid();
}
