package ru.moleculus.moveme.view;

import ru.moleculus.moveme.data.beans.Order;

/**
 * Created by Oleg on 25.03.2016.
 */
public interface CreateOrderView extends BaseView{

    void sendOrder(Order order);

    void onOrderCreate(Order order);

    void onOrderUpdated(Order order);

}
