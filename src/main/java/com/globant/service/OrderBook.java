package com.globant.service;

import com.globant.models.*;

import java.math.BigDecimal;
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
        if(buyOrders.size() > 0 && sellOrders.size() > 0) matchOrders();
    }

    private void matchOrders(){
        Order[] matchedOrders = findMatchingOrders();
        if (matchedOrders != null){
            processTransaction(matchedOrders[0], matchedOrders[1]);
            removeMatchedOrders(matchedOrders[0], matchedOrders[1]);
        }
    }

    private Order[] findMatchingOrders(){
        for(Order buyOrder : buyOrders){
            for(Order sellOrder : sellOrders){
                if(ordersMatch(buyOrder, sellOrder)){
                    return new Order[]{buyOrder, sellOrder};
                }
            }
        }
        return null;
    }

    private boolean ordersMatch(Order buyOrder, Order sellOrder){
        if(!buyOrder.getCryptoCurrency().equals(sellOrder.getCryptoCurrency())){
            return false;
        }
        if (buyOrder.getAmount().compareTo(sellOrder.getAmount()) != 0){
            return false;
        }
        if (buyOrder.getUser().equals(sellOrder.getUser())){
            return false;
        }
        BigDecimal maxPrice = ((BuyOrder) buyOrder).getMaxPrice();
        BigDecimal minPrice = ((SellOrder) sellOrder).getMinPrice();
        return maxPrice.compareTo(minPrice) >= 0;
    }

    private void processTransaction(Order buyOrder, Order sellOrder){
        CryptoCurrency cryptoCurrency = buyOrder.getCryptoCurrency();
        BigDecimal amount = buyOrder.getAmount();
        BigDecimal maxPrice = ((BuyOrder) buyOrder).getMaxPrice();
        BigDecimal minPrice = ((SellOrder) sellOrder).getMinPrice();
        User buyer = buyOrder.getUser();
        User seller = sellOrder.getUser();
        buyer.getWallet().depositCrypto(cryptoCurrency, amount);
        buyer.getWallet().depositFiat(maxPrice.subtract(minPrice));
        seller.getWallet().depositFiat(minPrice);
    }

    private void removeMatchedOrders(Order buyOrder, Order sellOrder){
        buyOrders.remove(buyOrder);
        sellOrders.remove(sellOrder);
    }
}
