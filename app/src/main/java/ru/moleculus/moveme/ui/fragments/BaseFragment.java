package ru.moleculus.moveme.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.presenters.impl.UserInfoViewPresenterImpl;
import ru.moleculus.moveme.view.BaseView;

/**
 * Created by Oleg on 08.03.2016.
 */
public class BaseFragment extends Fragment implements BaseView {


    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceView) {
        super.onCreate(savedInstanceView);
        initProgressView();
    }

    @Override
    public void initProgressView() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getContext().getString(R.string.progress_dialog_message));
    }

    @Override
    public void showProgressView() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void hideProgressView() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        hideProgressView();
        if (getContext() != null)
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}
