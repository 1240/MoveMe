package ru.moleculus.moveme.presenters.impl;

import ru.moleculus.moveme.BaseConstants;
import ru.moleculus.moveme.callbacks.BaseRequestCallback;
import ru.moleculus.moveme.data.SharedManager;
import ru.moleculus.moveme.net.ApiService;
import ru.moleculus.moveme.net.beans.moveme.OrdersResponse;
import ru.moleculus.moveme.presenters.OrdersViewPresenter;
import ru.moleculus.moveme.view.OrdersView;

/**
 * Created by Oleg on 12.02.2016.
 */
public class OrdersViewPresenterImpl implements OrdersViewPresenter, BaseRequestCallback<OrdersResponse> {

    private OrdersView ordersView;

    public OrdersViewPresenterImpl(OrdersView view) {
        this.ordersView = view;
    }

    @Override
    public void requestOrders(int mode) {
        switch (mode) {
            case OrdersView.ORDERS_ALL:
                requestInteractor.makeRequest(ApiService.getInstance().getMoveMeApi().getOrders(
                        SharedManager.getToken(ordersView.getContext())), this);
                break;
            case OrdersView.ORDERS_MY:
                requestInteractor.makeRequest(ApiService.getInstance().getMoveMeApi().getMyOrders(
                        SharedManager.getToken(ordersView.getContext())), this);
                break;
            case BaseConstants.ORDERS_TEMPLATE:
                requestInteractor.makeRequest(ApiService.getInstance().getMoveMeApi().getTemplates(
                        SharedManager.getToken(ordersView.getContext())), this);
                break;
        }
    }

    @Override
    public void onRequestError(String message) {
        ordersView.showErrorMessage(message);
    }

    @Override
    public void onRequestSuccess(OrdersResponse ordersResponse) {
        ordersView.showOrders(ordersResponse.getOrders());
    }
}
