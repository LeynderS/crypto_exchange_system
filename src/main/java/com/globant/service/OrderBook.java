package com.globant.service;

import com.globant.models.BuyOrder;
import com.globant.models.Order;
import com.globant.models.SellOrder;

import java.util.ArrayList;
import java.util.List;

public class OrderBook {
    private static OrderBook instance;
    private final List<Order> buyOrders = new ArrayList<>();
    private final List<Order> sellOrders = new ArrayList<>();
    private OrderBook() {
    }

    public static OrderBook getInstance() {
        if (instance == null) {
            instance = new OrderBook();
        }
        return instance;
    }

    public void addOrder(Order order) {
        if (order instanceof BuyOrder) {
            buyOrders.add(order);
            System.out.println(buyOrders.size());
            buyOrders.forEach(System.out::println);
        } else if(order instanceof SellOrder) {
            sellOrders.add(order);
            System.out.println(sellOrders.size());
        }
    }
}
