package ru.moleculus.moveme.view;

/**
 * Created by Oleg on 25.02.2016.
 */
public interface SendSmsCodeView extends BaseView {

    void onAuthSuccessfully(String token, boolean isRegistered);

}
