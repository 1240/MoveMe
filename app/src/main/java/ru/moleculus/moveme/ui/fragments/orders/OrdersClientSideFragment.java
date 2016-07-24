package ru.moleculus.moveme.ui.fragments.orders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.noisyz.databindinglibrary.bind.base.impl.adapter.BindAdapter;

import java.util.ArrayList;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.data.beans.Order;
import ru.moleculus.moveme.ui.activity.CreateOrderActivity;

/**
 * Created by Oleg on 16.02.2016.
 */
public class OrdersClientSideFragment extends AbsOrdersListFragment implements Toolbar.OnMenuItemClickListener {

    protected int mode = ORDERS_ALL;

    public OrdersClientSideFragment() {
    }

    public static OrdersClientSideFragment newInstance() {
        return new OrdersClientSideFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.client_orders_list, null);
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        getPresenter().requestOrders(mode);
    }

    @Override
    protected Toolbar initToolbar() {
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_orders_client_side_toolbar);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.orders);
        return toolbar;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_evac || item.getItemId() == R.id.action_cargo || item.getItemId() == R.id.action_pass) {
            Intent intent = new Intent(getContext(), CreateOrderActivity.class);
            switch (item.getItemId()) {
                case R.id.action_evac:
                    intent.putExtra(CreateOrderActivity.EXTRA_ORDER_TYPE, CreateOrderActivity.ORDER_TYPE_EVAC);
                    break;
                case R.id.action_cargo:
                    intent.putExtra(CreateOrderActivity.EXTRA_ORDER_TYPE, CreateOrderActivity.ORDER_TYPE_CARGO);
                    break;
                case R.id.action_pass:
                    intent.putExtra(CreateOrderActivity.EXTRA_ORDER_TYPE, CreateOrderActivity.ORDER_TYPE_PASS);
                    break;
            }
            startActivity(intent);
        }
        return true;
    }

    @Override
    public View getEmptyListTooltip() {
        return View.inflate(getContext(), R.layout.client_side_order_list_empty_tooltip, null);
    }

    @Override
    public void showOrders(ArrayList<Order> itemList) {
        if (!itemList.isEmpty()) {
            BindAdapter<Order> adapter = new BindAdapter<>(getContext(), itemList, R.layout.orders_list_item);
            adapter.setOnItemClickListener(this);
            getListView().setAdapter(adapter);
            hideEmptyListTooltip();
        } else {
            showEmptyListTooltip();
        }
        hideProgressView();
    }
}
