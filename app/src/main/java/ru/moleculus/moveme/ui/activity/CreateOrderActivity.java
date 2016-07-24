package ru.moleculus.moveme.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.data.beans.Location;
import ru.moleculus.moveme.data.beans.Order;
import ru.moleculus.moveme.ui.fragments.orders.BaseCreateOrderFragment;

/**
 * Created by Oleg on 18.02.2016.
 */
public class CreateOrderActivity extends BaseChildActivity {


    @Override
    protected Fragment getChildFragment() {
        Bundle extras = getIntent().getExtras();
        int orderType = extras.getInt(EXTRA_ORDER_TYPE);
        Order order = (Order) extras.getSerializable(EXTRA_ORDER);
        if(order==null){
            order = new Order();
        }
        Location location = (Location) extras.getSerializable(EXTRA_LOCATION);
        if(location!=null){
            order.getLocations().add(location);
        }
        Fragment fragment = BaseCreateOrderFragment.newInstance(order, orderType);
        switch (orderType) {
            case ORDER_TYPE_EVAC:
                getSupportActionBar().setTitle(R.string.new_evac_title);
                break;
            case ORDER_TYPE_PASS:
                getSupportActionBar().setTitle(R.string.pass);
                break;
            case ORDER_TYPE_CARGO:
                getSupportActionBar().setTitle(R.string.cargo);
                break;
        }
        return fragment;
    }

}
