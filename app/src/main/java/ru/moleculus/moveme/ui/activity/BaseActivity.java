package ru.moleculus.moveme.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import ru.moleculus.moveme.BaseConstants;

/**
 * Created by Oleg on 10.03.2016.
 */
public class BaseActivity extends AppCompatActivity implements BaseConstants{


    @Override
    public void onPause() {
        super.onPause();
        hideKeyboard();
    }

    @Override
    public void onStop(){
        super.onStop();
        getWindow().getDecorView().clearFocus();
    }

    @Override
    public void onResume(){
        super.onResume();
        getWindow().getDecorView().clearFocus();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        hideKeyboard();
    }

    private void hideKeyboard(){
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
