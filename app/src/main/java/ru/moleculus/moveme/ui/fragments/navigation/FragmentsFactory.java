package ru.moleculus.moveme.ui.fragments.navigation;

import android.support.v4.app.Fragment;

import ru.moleculus.moveme.BaseConstants;
import ru.moleculus.moveme.data.beans.User;
import ru.moleculus.moveme.ui.fragments.CarsFragment;
import ru.moleculus.moveme.ui.fragments.orders.MapFragment;
import ru.moleculus.moveme.ui.fragments.orders.TemplateListFragment;
import ru.moleculus.moveme.ui.fragments.user.PriceFragment;
import ru.moleculus.moveme.ui.fragments.orders.OrdersClientSideFragment;
import ru.moleculus.moveme.ui.fragments.orders.OrdersWorkerSideFragment;
import ru.moleculus.moveme.ui.fragments.user.UserProfileFragment;


public class FragmentsFactory implements BaseConstants {
    private static final int FRAGMENT_MAP = 1;
    private static final int FRAGMENT_ORDERS = 2;
    private static final int FRAGMENT_CUSTOM = 3;
    private static final int FRAGMENT_PROFILE = 4;
    private static final int FRAGMENT_PRICE = 5;

    public static Fragment makeFragment(User user, int position) {
        switch (user.getTypeOfAccount()) {
            case BaseConstants.TYPE_ACCOUNT_CLIENT:
                return getClientFragments(position);
            case BaseConstants.TYPE_ACCOUNT_WORKER:
                return getWorkerFragments(position);
        }
        return null;
    }

    private static Fragment getClientFragments(int position) {
        switch (position) {
            case FRAGMENT_MAP:
                return MapFragment.newInstance();
            case FRAGMENT_ORDERS:
                return OrdersClientSideFragment.newInstance();
            case FRAGMENT_CUSTOM:
                return TemplateListFragment.newInstance();
            case FRAGMENT_PROFILE:
                return UserProfileFragment.newInstance();
            case FRAGMENT_PRICE:
                return PriceFragment.newInstance();
        }
        return null;
    }

    private static Fragment getWorkerFragments(int position) {
        switch (position) {
            case FRAGMENT_MAP:
                return MapFragment.newInstance();
            case FRAGMENT_ORDERS:
                return OrdersWorkerSideFragment.newInstance();
            case FRAGMENT_CUSTOM:
                return CarsFragment.newInstance();
            case FRAGMENT_PROFILE:
                return UserProfileFragment.newInstance();
        }
        return null;
    }
}
