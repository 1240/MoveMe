package ru.moleculus.moveme.ui.fragments.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noisyz.databindinglibrary.bind.base.impl.adapter.BindAdapter;

import java.util.ArrayList;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.data.beans.Order;
import ru.moleculus.moveme.view.OrdersView;

/**
 * Created by Oleg on 12.02.2016.
 */
public class OrdersListFragment extends AbsOrdersListFragment implements OrdersView {

    public OrdersListFragment() {
    }

    protected int mode;

    public static OrdersListFragment newInstance(int mode) {
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_MODE, mode);
        OrdersListFragment fragment = new OrdersListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mode = getArguments().getInt(EXTRA_MODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.listview_fragment_layout, null);
    }

    @Override
    public void showOrders(ArrayList<Order> itemList) {
        if (!itemList.isEmpty()) {
            BindAdapter<Order> adapter = null;
            switch (mode) {
                case ORDERS_ALL:
                    adapter = new BindAdapter<>(getContext(), itemList, R.layout.orders_list_item);
                    break;
                case ORDERS_MY:
                    adapter = new BindAdapter<>(getContext(), itemList, R.layout.orders_list_item);
                    break;

            }
            if (adapter != null)
                adapter.setOnItemClickListener(this);
            getListView().setAdapter(adapter);
            hideEmptyListTooltip();
        } else {
            showEmptyListTooltip();
        }
        hideProgressView();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().requestOrders(mode);
    }

    @Override
    public View getEmptyListTooltip() {
        switch (mode) {
            case ORDERS_MY:
                return View.inflate(getContext(), R.layout.worker_side_order_my_list_empty_tooltip, null);
            case ORDERS_ALL:
                return View.inflate(getContext(), R.layout.worker_side_order_all_list_empty_tooltip, null);
            case ORDERS_TEMPLATE:
                return View.inflate(getContext(), R.layout.client_side_template_list_empty_tooltip, null);
        }
        return null;
    }
}
