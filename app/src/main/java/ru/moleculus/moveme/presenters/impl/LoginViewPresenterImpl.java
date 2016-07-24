package ru.moleculus.moveme.presenters.impl;

import ru.moleculus.moveme.callbacks.BaseRequestCallback;
import ru.moleculus.moveme.data.SharedManager;
import ru.moleculus.moveme.net.ApiService;
import ru.moleculus.moveme.net.beans.moveme.AuthResponse;
import ru.moleculus.moveme.net.beans.moveme.BaseMoveMeResponse;
import ru.moleculus.moveme.net.beans.moveme.SendNumberResponse;
import ru.moleculus.moveme.presenters.LoginViewPresenter;
import ru.moleculus.moveme.view.LoginView;
import ru.moleculus.moveme.view.RegistrationView;
import ru.moleculus.moveme.view.SendNumberView;
import ru.moleculus.moveme.view.SendSmsCodeView;

/**
 * Created by Oleg on 02.03.2016.
 */
public class LoginViewPresenterImpl implements LoginViewPresenter, BaseRequestCallback<BaseMoveMeResponse> {
    private LoginView loginView;

    private static String phoneNumber;
    private static boolean isRegistered;

    public LoginViewPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void requestToken(String smsCode) {
        requestInteractor.makeRequest(ApiService.getInstance().getMoveMeApi().getToken(phoneNumber, smsCode), this);
    }

    @Override
    public void sendNumber(String number) {
        phoneNumber = number.replaceAll("[\\(,\\),\\-]","");
        requestInteractor.makeRequest(ApiService.getInstance().getMoveMeApi().requestSmsCode(phoneNumber), this);
    }

    public void sendNumber() {
        sendNumber(phoneNumber);
    }

    @Override
    public void registerUser(int typeOfAccount, String firstName, String lastName,
                             String middleName, String mail) {
        requestInteractor.makeRequest(ApiService.getInstance().getMoveMeApi().
                registerUser(SharedManager.getToken(loginView.getContext()),
                        typeOfAccount, firstName, lastName, middleName, mail), this);
    }

    @Override
    public void onRequestError(String message) {
        loginView.showErrorMessage(message);
    }

    @Override
    public void onRequestSuccess(BaseMoveMeResponse baseResponse) {
        if (baseResponse instanceof SendNumberResponse) {
            SendNumberView sendNumberView = loginView;
            SendNumberResponse response = (SendNumberResponse) baseResponse;
            sendNumberView.onNumberSended();
            isRegistered = response.isRegistered();
        } else if (baseResponse instanceof AuthResponse) {
            SendSmsCodeView sendSmsCodeView = loginView;
            AuthResponse response = (AuthResponse) baseResponse;
            sendSmsCodeView.onAuthSuccessfully(response.getToken(), isRegistered);
        } else {
            RegistrationView registrationView = loginView;
            registrationView.onRegistrationFinish();
        }

    }
}
