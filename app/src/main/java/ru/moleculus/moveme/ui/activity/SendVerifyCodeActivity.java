package ru.moleculus.moveme.ui.activity;

import android.support.v4.app.Fragment;

import ru.moleculus.moveme.ui.fragments.verify.EnterVerifyCodeFragment;

/**
 * Created by Oleg on 04.04.2016.
 */
public class SendVerifyCodeActivity extends BaseChildActivity {
    @Override
    protected Fragment getChildFragment() {
        return EnterVerifyCodeFragment.newInstance();
    }
}
