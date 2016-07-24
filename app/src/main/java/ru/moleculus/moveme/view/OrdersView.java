package ru.moleculus.moveme.view;

import java.util.ArrayList;

import ru.moleculus.moveme.data.beans.Order;

/**
 * Created by Oleg on 12.02.2016.
 */
public interface OrdersView extends BaseView{

    void showOrders(ArrayList<Order> itemList);

}
