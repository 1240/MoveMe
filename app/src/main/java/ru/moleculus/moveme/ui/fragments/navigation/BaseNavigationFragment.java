package ru.moleculus.moveme.ui.fragments.navigation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.ui.fragments.BaseFragment;

/**
 * Created by Oleg on 07.03.2016.
 */
public class BaseNavigationFragment extends BaseFragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar fragmentNavigationToolbar = initToolbar();
        if (fragmentNavigationToolbar != null) {
            fragmentNavigationToolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
            fragmentNavigationToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavigationDrawerCallbacks callbacks =
                            (NavigationDrawerCallbacks)
                                    getActivity().getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
                    callbacks.onClickNavigationIcon();
                }
            });
        }
    }

    protected Toolbar initToolbar() {
        return null;
    }

}
