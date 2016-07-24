package ru.moleculus.moveme.ui.fragments.navigation;

import android.support.v4.app.Fragment;

/**
 * Created by Oleg on 11.03.2016.
 */
public interface NavigationDrawerCallbacks {

    void onNavigationDrawerItemSelected(Fragment fragment);

    void onClickNavigationIcon();
}
