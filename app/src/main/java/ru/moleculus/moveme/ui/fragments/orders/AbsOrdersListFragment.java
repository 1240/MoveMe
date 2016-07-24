package ru.moleculus.moveme.ui.fragments.orders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.noisyz.databindinglibrary.bind.base.impl.adapter.ConvertBindAdapter;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.data.beans.Order;
import ru.moleculus.moveme.presenters.OrdersViewPresenter;
import ru.moleculus.moveme.presenters.impl.OrdersViewPresenterImpl;
import ru.moleculus.moveme.ui.activity.CreateOrderActivity;
import ru.moleculus.moveme.ui.fragments.navigation.BaseNavigationFragment;
import ru.moleculus.moveme.view.OrdersView;

/**
 * Created by Oleg on 16.02.2016.
 */
public abstract class AbsOrdersListFragment extends BaseNavigationFragment implements OrdersView, ConvertBindAdapter.OnItemClickListener<Order> {
    private View emptyListTooltip;
    private ListView listView;
    private OrdersViewPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new OrdersViewPresenterImpl(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.list_view);
        emptyListTooltip = getEmptyListTooltip();
        if (emptyListTooltip != null) {
            ((LinearLayout) view.findViewById(R.id.empty_list_tooltip)).addView(emptyListTooltip);
            hideEmptyListTooltip();
        }
        showProgressView();
    }

    protected void showEmptyListTooltip() {
        if (emptyListTooltip != null)
            emptyListTooltip.setVisibility(View.VISIBLE);
    }

    protected void hideEmptyListTooltip() {
        if (emptyListTooltip != null)
            emptyListTooltip.setVisibility(View.GONE);
    }

    protected OrdersViewPresenter getPresenter() {
        return presenter;
    }

    protected ListView getListView() {
        return listView;
    }

    abstract View getEmptyListTooltip();

    @Override
    public void showProgressView() {
        if (getView() != null)
            getView().findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressView() {
        if (getView() != null)
            getView().findViewById(R.id.progressBar).setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(int position, Order o) {
        Intent intent = new Intent(getContext(), CreateOrderActivity.class);
        intent.putExtra(CreateOrderActivity.EXTRA_ORDER, o);
        intent.putExtra(CreateOrderActivity.EXTRA_ORDER_TYPE, o.getType());
        startActivity(intent);
    }
}
