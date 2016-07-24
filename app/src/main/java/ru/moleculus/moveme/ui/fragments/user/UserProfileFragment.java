package ru.moleculus.moveme.ui.fragments.user;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.noisyz.customeelements.dialog.AlertDialogFactory;
import com.noisyz.customeelements.utils.SimpleImageUtils;
import com.noisyz.databindinglibrary.annotations.field.SimpleFieldType;
import com.noisyz.databindinglibrary.annotations.type;
import com.noisyz.databindinglibrary.bind.base.impl.ObjectDataBinder;
import com.noisyz.databindinglibrary.bind.base.UIBinder;

import java.io.File;
import java.lang.reflect.Field;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.data.SharedManager;
import ru.moleculus.moveme.data.beans.Passport;
import ru.moleculus.moveme.data.beans.User;
import ru.moleculus.moveme.ui.activity.CardInfoActivity;
import ru.moleculus.moveme.ui.activity.EditPassportActivity;
import ru.moleculus.moveme.ui.activity.SendNumberActivity;
import ru.moleculus.moveme.ui.activity.VerifyMainActivity;

/**
 * Created by Oleg on 02.03.2016.
 */
public class UserProfileFragment extends AbsUserFragment implements View.OnClickListener, Toolbar.OnMenuItemClickListener {

    private Dialog logOutDialog, imagePickDialog;
    private MenuItem saveButton;

    @SimpleFieldType(type.TEXT)
    private String userPassport;

    public UserProfileFragment() {
    }

    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_profile_layout, null);
        initLogOutDialog();
        imagePickDialog = AlertDialogFactory.initDialog(this, AlertDialogFactory.DIALOG_USER_IMAGE);
        getDataBinder().newBinder("UserPassport",
                new ObjectDataBinder<>(this).
                        registerView(view));
        return view;
    }

    private void initLogOutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getContext().getString(R.string.log_out_message));
        builder.setTitle(getContext().getString(R.string.log_out_title));
        builder.setPositiveButton(getContext().getString(R.string.log_out_accept), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedManager.saveToken(getActivity(), "");
                Intent intent = new Intent(getActivity(), SendNumberActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
        builder.setNegativeButton(getContext().getString(R.string.log_out_decline), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logOutDialog.dismiss();
            }
        });
        logOutDialog = builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!User.getInstance().getPassports().isEmpty()) {
            Passport passport = User.getInstance().getPassports().get(0);
            userPassport = (passport.getSeries() + " " + passport.getNumber());
        }
        getDataBinder().bindUI();
    }

    @Override
    public void showUserData(User user) {
        getView().findViewById(R.id.user_profile_header).setOnClickListener(this);
        getView().findViewById(R.id.ll_balance_refill).setOnClickListener(this);
        getView().findViewById(R.id.ed_user_passport).setOnClickListener(this);
        getView().findViewById(R.id.log_out).setOnClickListener(this);
        getView().findViewById(R.id.verify).setOnClickListener(this);
        super.showUserData(user);
        saveButton.setVisible(false);
    }

    @Override
    public void notifyDataUpdated() {
        getPresenter().requestUser();
        showProgressView();
    }

    @Override
    public void sendUserPhotoFile(File file) {
        getPresenter().loadUserPhoto(file);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_balance_refill:
                Intent intent = new Intent(getActivity(), CardInfoActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.ed_user_passport:
                intent = new Intent(getActivity(), EditPassportActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.user_profile_header:
                imagePickDialog.show();
                break;
            case R.id.log_out:
                logOutDialog.show();
                break;
            case R.id.verify:
                intent = new Intent(getContext(), VerifyMainActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                showProgressView();
                getPresenter().updateUser(User.getInstance());
                break;
        }
        return true;
    }

    @Override
    protected Toolbar initToolbar() {
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.profile_screen_title));
        toolbar.inflateMenu(R.menu.menu_register);
        saveButton = toolbar.getMenu().findItem(R.id.action_save);
        saveButton.setVisible(false);
        toolbar.setOnMenuItemClickListener(this);
        return toolbar;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SimpleImageUtils.handleOnImagePickActivityResult(getContext(), requestCode, resultCode, data,
                new SimpleImageUtils.OnImagePickedCallback() {
                    @Override
                    public void onImagePicked(File file) {
                        sendUserPhotoFile(file);
                    }
                });
    }

    @Override
    public void onDataUpdated(UIBinder uiBinder, Object object, String propertyName, Object value) {
        saveButton.setVisible(true);
    }
}
