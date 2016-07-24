package ru.moleculus.moveme.ui.activity;

import android.support.v4.app.Fragment;

import ru.moleculus.moveme.ui.fragments.verify.LoadDocsFragment;

/**
 * Created by Oleg on 04.04.2016.
 */
public class LoadDocsActivity extends BaseChildActivity {
    @Override
    protected Fragment getChildFragment() {
        return LoadDocsFragment.newInstance();
    }
}
