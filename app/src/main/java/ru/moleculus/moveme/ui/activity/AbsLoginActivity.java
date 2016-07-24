package ru.moleculus.moveme.ui.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.view.LoginView;

/**
 * Created by Oleg on 02.03.2016.
 */
public class AbsLoginActivity extends BaseActivity implements LoginView {

    private ProgressDialog pDialog;

    private BroadcastReceiver loginFinishReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginFinishReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (ACTION_LOGIN.equals(intent.getAction())) {
                    finish();
                }
            }
        };
        IntentFilter filter = new IntentFilter(ACTION_LOGIN);
        registerReceiver(loginFinishReceiver, filter);
        initProgressView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(loginFinishReceiver);
    }

    @Override
    public void onRegistrationFinish() {

    }

    @Override
    public boolean isDataValid() {
        return false;
    }

    @Override
    public void onNumberSended() {

    }

    @Override
    public void onAuthSuccessfully(String token, boolean isRegistered) {
        Intent login = new Intent(ACTION_LOGIN);
        sendBroadcast(login);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        hideProgressView();
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initProgressView() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.progress_dialog_message));
    }

    @Override
    public void showProgressView() {
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }

    @Override
    public void hideProgressView() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
}
