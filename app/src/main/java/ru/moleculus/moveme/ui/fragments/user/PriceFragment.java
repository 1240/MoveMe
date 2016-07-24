package ru.moleculus.moveme.ui.fragments.user;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.noisyz.databindinglibrary.bind.base.UIBinder;

import java.lang.reflect.Field;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.data.beans.User;

/**
 * Created by Oleg on 02.03.2016.
 */
public class PriceFragment extends AbsUserFragment implements Toolbar.OnMenuItemClickListener{

    public PriceFragment(){}
    private MenuItem saveButton;

    public static PriceFragment newInstance(){
        return new PriceFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.price_fragment, null);
        return view;
    }

    @Override
    protected Toolbar initToolbar() {
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.price_screen_title));
        toolbar.inflateMenu(R.menu.menu_register);
        saveButton = toolbar.getMenu().findItem(R.id.action_save);
        saveButton.setVisible(false);
        toolbar.setOnMenuItemClickListener(this);
        return toolbar;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                showProgressView();
                getPresenter().updateUserPrice(User.getInstance());
                break;
        }
        return true;
    }

    @Override
    public void notifyDataUpdated() {
        getDataBinder().bindUI();
        saveButton.setVisible(false);
        hideProgressView();
    }

    @Override
    public void onDataUpdated(UIBinder uiBinder, Object object, String propertyName, Object value) {
        saveButton.setVisible(true);
    }
}
