package ru.moleculus.moveme.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.ui.fragments.user.PassportFragment;
import ru.moleculus.moveme.view.UserInfoView;

/**
 * Created by Oleg on 18.02.2016.
 */
public class EditPassportActivity extends BaseChildActivity{

    @Override
    protected Fragment getChildFragment() {
        return PassportFragment.newInstance();
    }
}
