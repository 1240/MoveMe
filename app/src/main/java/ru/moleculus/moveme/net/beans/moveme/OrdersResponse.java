package ru.moleculus.moveme.net.beans.moveme;

import java.util.ArrayList;

import ru.moleculus.moveme.data.beans.Order;

/**
 * Created by Oleg on 11.02.2016.
 */
public class OrdersResponse extends BaseMoveMeResponse {
    private ArrayList<Order> orders;
    private ArrayList<Order> templates;

    public ArrayList<Order> getOrders(){
        return orders==null?templates:orders;
    }
}
