package ru.moleculus.moveme.ui.fragments.orders;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.noisyz.databindinglibrary.bind.base.impl.adapter.BindAdapter;

import java.util.ArrayList;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.data.beans.Order;

/**
 * Created by Oleg on 26.03.2016.
 */
public class TemplateListFragment extends OrdersClientSideFragment {

    public TemplateListFragment() {
    }

    public static TemplateListFragment newInstance() {
        return new TemplateListFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mode = ORDERS_TEMPLATE;
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected Toolbar initToolbar() {
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.patterns);
        return toolbar;
    }

    @Override
    public View getEmptyListTooltip() {
        return View.inflate(getContext(), R.layout.client_side_template_list_empty_tooltip, null);
    }

    @Override
    public void showOrders(ArrayList<Order> itemList) {
        for (Order order : itemList)
            order.setId(-1);
        if (!itemList.isEmpty()) {
            BindAdapter<Order> adapter = new BindAdapter<>(getContext(), itemList, R.layout.template_list_item);
            adapter.setOnItemClickListener(this);
            getListView().setAdapter(adapter);
            hideEmptyListTooltip();
        } else {
            showEmptyListTooltip();
        }
        hideProgressView();
    }
}
