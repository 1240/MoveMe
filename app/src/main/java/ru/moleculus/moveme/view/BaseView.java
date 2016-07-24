package ru.moleculus.moveme.view;

import android.app.ProgressDialog;
import android.content.Context;

import ru.moleculus.moveme.BaseConstants;

/**
 * Created by Oleg on 25.02.2016.
 */
public interface BaseView extends BaseConstants {

    Context getContext();

    void showErrorMessage(String errorMessage);

    void initProgressView();

    void showProgressView();

    void hideProgressView();
}
