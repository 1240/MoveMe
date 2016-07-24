package ru.moleculus.moveme.ui.fragments.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.noisyz.databindinglibrary.bind.BindingManager;
import com.noisyz.databindinglibrary.bind.base.impl.ObjectDataBinder;
import com.noisyz.databindinglibrary.bind.base.UIBinder;
import com.noisyz.databindinglibrary.callback.DataUpdatedCallback;

import java.io.File;
import java.lang.reflect.Field;

import ru.moleculus.moveme.data.SharedManager;
import ru.moleculus.moveme.data.beans.User;
import ru.moleculus.moveme.presenters.UserInfoViewPresenter;
import ru.moleculus.moveme.presenters.impl.UserInfoViewPresenterImpl;
import ru.moleculus.moveme.ui.activity.SendNumberActivity;
import ru.moleculus.moveme.ui.fragments.navigation.BaseNavigationFragment;
import ru.moleculus.moveme.view.UserInfoView;

/**
 * Created by Oleg on 05.03.2016.
 */
public abstract class AbsUserFragment extends BaseNavigationFragment implements UserInfoView, DataUpdatedCallback {

    private UserInfoViewPresenter presenter;
    private BindingManager bindingManager;
    private ObjectDataBinder<User> userObjectDataBinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingManager = BindingManager.newInstance().setDataUpdatedCallback(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new UserInfoViewPresenterImpl(this);
        presenter.requestUser();
        showProgressView();
    }

    protected UserInfoViewPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void showUserData(User user) {
        hideProgressView();
        if (userObjectDataBinder == null) {
            userObjectDataBinder = new ObjectDataBinder<User>(user).registerView(this);
            bindingManager.newBinder("UserBinder", userObjectDataBinder);
        }
        bindingManager.bindUI();
        if (user.getTypeOfAccount() == TYPE_ACCOUNT_INVALID) {
            SharedManager.saveToken(getActivity(), "");
            Intent intent = new Intent(getActivity(), SendNumberActivity.class);
            getActivity().startActivity(intent);
            getActivity().finish();
        }
    }

    @Override
    public void notifyDataUpdated() {
        getActivity().finish();
    }

    protected BindingManager getDataBinder() {
        return bindingManager;
    }

    @Override
    public void notifyIdUpdated(long id) {

    }

    @Override
    public void sendPassportData() {

    }

    @Override
    public void sendCardData() {

    }

    @Override
    public void sendUserPhotoFile(File file) {

    }

    @Override
    public void sendUserFile(File file) {
        getPresenter().loadUserFile(file);
    }

    @Override
    public void onFileSent(String url) {

    }

    @Override
    public void onDataUpdated(UIBinder uiBinder, Object object, String propertyName, Object value) {

    }
}
