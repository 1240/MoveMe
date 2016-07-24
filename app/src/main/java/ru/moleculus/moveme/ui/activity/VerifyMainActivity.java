package ru.moleculus.moveme.ui.activity;

import android.support.v4.app.Fragment;

import ru.moleculus.moveme.ui.fragments.verify.VerifyMainFragment;

/**
 * Created by Oleg on 04.04.2016.
 */
public class VerifyMainActivity extends BaseChildActivity {
    @Override
    protected Fragment getChildFragment() {
        return VerifyMainFragment.newInstance();
    }
}
