package ru.moleculus.moveme.ui.fragments.orders;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.adapters.OrdersPagerAdapter;
import ru.moleculus.moveme.ui.fragments.navigation.BaseNavigationFragment;

/**
 * Created by Oleg on 16.02.2016.
 */
public class OrdersWorkerSideFragment extends BaseNavigationFragment {

    public OrdersWorkerSideFragment(){
    }

    public static OrdersWorkerSideFragment newInstance(){
        return new OrdersWorkerSideFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orders_workerside, null);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        OrdersPagerAdapter adapter = new OrdersPagerAdapter(getContext(), getChildFragmentManager());
        viewPager.setAdapter(adapter);
    }

    @Override
    protected Toolbar initToolbar() {
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.orders);
        return toolbar;
    }
}
