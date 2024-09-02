package com.globant.service;

import com.globant.exceptions.UserNotFoundException;
import com.globant.models.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderBook {
    private final List<Order> buyOrders = new ArrayList<>();
    private final List<Order> sellOrders = new ArrayList<>();
    private final TransactionService transactionService;

    public OrderBook(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void addOrder(Order order) {
        if (order instanceof BuyOrder) {
            buyOrders.add(order);
        } else if(order instanceof SellOrder) {
            sellOrders.add(order);
        }
        if(buyOrders.size() > 0 && sellOrders.size() > 0) matchOrders();
    }

    public List<Order> getUserOrders(User user){
        List<Order> userOrders = new ArrayList<>();
        for(Order order : buyOrders){
            if(order.getUser().equals(user)){
                userOrders.add(order);
            }
        }
        for(Order order : sellOrders){
            if(order.getUser().equals(user)){
                userOrders.add(order);
            }
        }
        if(userOrders.size() > 0){
            return List.copyOf(userOrders);
        }else{
            throw new UserNotFoundException();
        }
    }

    public void cancelUserOrder(User user, int orderIndex){
        List<Order> userOrders = getUserOrders(user);
        Order order = userOrders.get(orderIndex);
        if(order instanceof BuyOrder){
            order.getUser().getWallet().depositFiat(((BuyOrder) order).getMaxPrice());
            buyOrders.remove(order);
        }else if(order instanceof SellOrder){
            order.getUser().getWallet().depositCrypto(order.getCryptoCurrency(), ((SellOrder) order).getMinPrice());
            sellOrders.remove(order);
        }
    }

    private void matchOrders(){
        Order[] matchedOrders = findMatchingOrders();
        if (matchedOrders != null){
            transactionService.processTransaction(matchedOrders[0], matchedOrders[1]);
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

    private void removeMatchedOrders(Order buyOrder, Order sellOrder){
        buyOrders.remove(buyOrder);
        sellOrders.remove(sellOrder);
    }
}
