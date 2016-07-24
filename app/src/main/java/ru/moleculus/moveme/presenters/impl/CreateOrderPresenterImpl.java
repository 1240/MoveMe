package ru.moleculus.moveme.presenters.impl;

import ru.moleculus.moveme.callbacks.BaseRequestCallback;
import ru.moleculus.moveme.data.SharedManager;
import ru.moleculus.moveme.data.beans.Order;
import ru.moleculus.moveme.net.ApiService;
import ru.moleculus.moveme.net.beans.moveme.BaseMoveMeResponse;
import ru.moleculus.moveme.presenters.CreateOrderPresenter;
import ru.moleculus.moveme.view.CreateOrderView;

/**
 * Created by Oleg on 25.03.2016.
 */
public class CreateOrderPresenterImpl implements CreateOrderPresenter, BaseRequestCallback<BaseMoveMeResponse> {

    private Order order;
    private CreateOrderView createOrderView;
    private String token;
    private boolean needSavingPrice;

    public CreateOrderPresenterImpl(CreateOrderView createOrderView) {
        this.createOrderView = createOrderView;
    }

    @Override
    public void sendOrder(Order order, boolean needSavingPrice) {
        this.needSavingPrice = needSavingPrice;
        this.token = SharedManager.getToken(createOrderView.getContext());
        this.order = order;
        if (order.getId() == -1) {
            order.updateDateTime();
            requestInteractor.makeRequest(ApiService.getInstance().getMoveMeApi().
                    sendOrder(token, order.getRequestHashMap(0)), this);
        } else {
            requestInteractor.makeRequest(ApiService.getInstance().getMoveMeApi().
                    sendOrder(order.getId(), token, order.getRequestHashMap(0)), this);
        }
    }

    @Override
    public void onRequestError(String message) {
        createOrderView.showErrorMessage(message);
    }

    @Override
    public void onRequestSuccess(BaseMoveMeResponse baseResponse) {
        if (needSavingPrice) {
            needSavingPrice = false;
            requestInteractor.makeRequest(ApiService.getInstance().getMoveMeApi().setPrice(
                    token, order.getTariffFirst2Hours(), order.getTariffAfter2Hours(),
                    order.getTariffOutOfMkad()), this);
        } else {
            if (order.getId() == -1) {
                order.setId(baseResponse.getId());
                createOrderView.onOrderCreate(order);
            } else {
                createOrderView.onOrderUpdated(order);
            }
        }
    }
}
