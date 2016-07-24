package ru.moleculus.moveme.presenters;

import ru.moleculus.moveme.data.beans.Order;

/**
 * Created by Oleg on 25.03.2016.
 */
public interface CreateOrderPresenter extends BasePresenter{

    void sendOrder(Order order, boolean needSavingPrice);


}
