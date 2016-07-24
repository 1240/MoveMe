package ru.moleculus.moveme.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import ru.moleculus.moveme.R;
import ru.moleculus.moveme.data.SharedManager;
import ru.moleculus.moveme.data.beans.Cargo;
import ru.moleculus.moveme.ui.fragments.navigation.NavigationDrawerFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        if (SharedManager.getToken(this).isEmpty()) {
            moveToLogin();
        } else if (!SharedManager.isRegistered(this)) {
            moveToRegister();
        }
    }

    private void moveToLogin() {
        Intent intent = new Intent(this, SendNumberActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void moveToRegister(){
        Intent intent = new Intent(this, RegisterUserActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
