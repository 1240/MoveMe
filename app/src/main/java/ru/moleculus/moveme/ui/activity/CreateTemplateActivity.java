package ru.moleculus.moveme.ui.activity;

import android.support.v4.app.Fragment;

import ru.moleculus.moveme.ui.fragments.orders.CreateTemplateFragment;

/**
 * Created by Oleg on 25.03.2016.
 */
public class CreateTemplateActivity extends BaseChildActivity {
    @Override
    protected Fragment getChildFragment() {
        return CreateTemplateFragment.newInstance(
                getIntent().getExtras().getLong(EXTRA_ID));
    }
}
