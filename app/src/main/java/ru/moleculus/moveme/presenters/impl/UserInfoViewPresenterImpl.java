package ru.moleculus.moveme.presenters.impl;

import java.io.File;

import retrofit.mime.TypedFile;
import ru.moleculus.moveme.callbacks.BaseRequestCallback;
import ru.moleculus.moveme.data.SharedManager;
import ru.moleculus.moveme.data.beans.Passport;
import ru.moleculus.moveme.data.beans.PaymentMethod;
import ru.moleculus.moveme.data.beans.User;
import ru.moleculus.moveme.net.ApiConst;
import ru.moleculus.moveme.net.ApiService;
import ru.moleculus.moveme.net.beans.moveme.BaseMoveMeResponse;
import ru.moleculus.moveme.net.beans.moveme.CardResponse;
import ru.moleculus.moveme.net.beans.moveme.FileUploadResponse;
import ru.moleculus.moveme.net.beans.moveme.PassportsResponse;
import ru.moleculus.moveme.net.beans.moveme.UserResponse;
import ru.moleculus.moveme.presenters.UserInfoViewPresenter;
import ru.moleculus.moveme.view.UserInfoView;

/**
 * Created by Oleg on 02.03.2016.
 */
public class UserInfoViewPresenterImpl implements UserInfoViewPresenter, BaseRequestCallback<BaseMoveMeResponse> {
    private UserInfoView userInfoView;
    private String token;

    public UserInfoViewPresenterImpl(UserInfoView userInfoView) {
        this.userInfoView = userInfoView;
        token = SharedManager.getToken(userInfoView.getContext());
    }

    @Override
    public void requestUser() {
        if (!token.isEmpty())
            requestInteractor.makeRequest(ApiService.getInstance().getMoveMeApi().getUser(token), this);
    }

    @Override
    public void updateUser(User user) {
        if (!token.isEmpty())
        requestInteractor.makeRequest(ApiService.getInstance().getMoveMeApi().updateUser(token,
                user.getFirstName(), user.getLastName(), user.getMiddleName(), user.getMail()), this);
    }


    @Override
    public void addPassportData(Passport passport) {
        if (passport.getId() == -1) {
            requestInteractor.makeRequest(ApiService.getInstance().getMoveMeApi().sendPassportData(token, passport.getRequestHashMap(0)), this);
        } else {
            requestInteractor.makeRequest(ApiService.getInstance().getMoveMeApi().sendPassportData(passport.getId(), token,
                    passport.getRequestHashMap(0)), this);
        }
    }

    @Override
    public void addCardData(PaymentMethod method) {
        if (method.getId() == -1) {
            requestInteractor.makeRequest(ApiService.getInstance().getMoveMeApi().sendCardData(
                    token, method.getRequestHashMap(0)), this);
        } else {
            requestInteractor.makeRequest(ApiService.getInstance().getMoveMeApi().sendCardData(method.getId(),
                    token, method.getRequestHashMap(0)), this);
        }
    }

    @Override
    public void loadUserPhoto(final File file) {
        requestInteractor.makeRequest(ApiService.getInstance().getMoveMeApi().uploadFile(token,
                new TypedFile(ApiConst.MULTIPART_DATA, file)), new BaseRequestCallback<FileUploadResponse>() {
            @Override
            public void onRequestError(String message) {
                userInfoView.showErrorMessage(message);
            }

            @Override
            public void onRequestSuccess(FileUploadResponse fileUploadResponse) {
                UserInfoViewPresenterImpl.this.onRequestError(fileUploadResponse.getMessage());
                requestInteractor.makeRequest(ApiService.getInstance().getMoveMeApi().updateUserPhoto(
                                token, fileUploadResponse.getUploadedPath()), UserInfoViewPresenterImpl.this
                );
            }
        });
    }

    @Override
    public void loadUserFile(File file) {
        requestInteractor.makeRequest(ApiService.getInstance().getMoveMeApi().uploadFile(token,
                new TypedFile(ApiConst.MULTIPART_DATA, file)), new BaseRequestCallback<FileUploadResponse>() {
            @Override
            public void onRequestError(String message) {
                userInfoView.showErrorMessage(message);
            }

            @Override
            public void onRequestSuccess(FileUploadResponse fileUploadResponse) {
                UserInfoViewPresenterImpl.this.onRequestError(fileUploadResponse.getMessage());
                userInfoView.onFileSent(fileUploadResponse.getUploadedPath());
            }
        });
    }


    @Override
    public void updateUserPrice(User user) {
        requestInteractor.makeRequest(ApiService.getInstance().getMoveMeApi().setPrice(
                token, user.getTariffFirst2Hours(), user.getTariffAfter2Hours(), user.getTariffOutOfMkad()
        ), this);
    }

    @Override
    public void onRequestError(String message) {
        userInfoView.showErrorMessage(message);
    }

    @Override
    public void onRequestSuccess(BaseMoveMeResponse baseResponse) {
        if (baseResponse instanceof UserResponse) {
            UserResponse userResponse = (UserResponse) baseResponse;
            User.init(userResponse.getUser());
            userInfoView.showUserData(userResponse.getUser());
        } else if (baseResponse instanceof CardResponse) {
            CardResponse cardResponse = (CardResponse) baseResponse;
            userInfoView.notifyIdUpdated(cardResponse.getId());
        } else if (baseResponse instanceof PassportsResponse) {
            PassportsResponse passportsResponse = (PassportsResponse) baseResponse;
            userInfoView.notifyIdUpdated(passportsResponse.getId());
        } else userInfoView.notifyDataUpdated();
    }
}
