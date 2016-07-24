package ru.moleculus.moveme.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.ui.fragments.orders.OrdersListFragment;
import ru.moleculus.moveme.view.OrdersView;

/**
 * Created by Oleg on 12.02.2016.
 */
public class OrdersPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;

    public OrdersPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<Fragment>();
        titles = new ArrayList<String>();
        fragments.add(OrdersListFragment.newInstance(OrdersView.ORDERS_ALL));
        titles.add(context.getString(R.string.orders_all));
        fragments.add(OrdersListFragment.newInstance(OrdersView.ORDERS_MY));
        titles.add(context.getString(R.string.orders_my));
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
