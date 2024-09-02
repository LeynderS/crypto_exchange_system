package com.globant.service;

import com.globant.exceptions.UserNotFoundException;
import com.globant.models.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderBook class is responsible for managing the buy and sell orders.
 * It has a list of buy orders and a list of sell orders.
 * It has a TransactionService object to process transactions.
 * It has methods to add orders, get user orders, and cancel user orders.
 * It has a private method to match orders and process transactions.
 */
public class OrderBook {
    private final List<Order> buyOrders = new ArrayList<>();
    private final List<Order> sellOrders = new ArrayList<>();
    private final TransactionService transactionService;

    public OrderBook(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Add an order to the buyOrders or sellOrders list.
    public void addOrder(Order order) {
        if (order instanceof BuyOrder) {
            buyOrders.add(order);
        } else if(order instanceof SellOrder) {
            sellOrders.add(order);
        }
        if(buyOrders.size() > 0 && sellOrders.size() > 0) matchOrders();
    }

    /**
     * This method returns a list of orders for a given user.
     * It iterates over the buyOrders and sellOrders lists to find the orders that belong to the user.
     * If the user has no orders, it throws a UserNotFoundException.
     * @param user
     * @return List<Order>
     */
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

    /**
     * It receives a user and an order index.
     * It gets the user orders using the getUserOrders method.
     * It gets the order from the user orders list using the order index.
     * If the order is a BuyOrder, it deposits the maxPrice to the user wallet and removes the order from the buyOrders list.
     * If the order is a SellOrder, it deposits the amount of Crypto to the user wallet and removes the order from the sellOrders list.
     * @param user
     * @param orderIndex
     */
    public void cancelUserOrder(User user, int orderIndex){
        List<Order> userOrders = getUserOrders(user);
        Order order = userOrders.get(orderIndex);
        if(order instanceof BuyOrder){
            order.getUser().getWallet().depositFiat(((BuyOrder) order).getMaxPrice());
            buyOrders.remove(order);
        }else if(order instanceof SellOrder){
            order.getUser().getWallet().depositCrypto(order.getCryptoCurrency(), order.getAmount());
            sellOrders.remove(order);
        }
    }

    /**
     * This method is called when there are buy and sell orders in the order book.
     * It iterates over the buyOrders list and the sellOrders list to find matching orders.
     * If it finds matching orders, it processes the transaction using the transactionService object.
     * It removes the matched orders from the buyOrders and sellOrders lists.
     */
    private void matchOrders(){
        Order[] matchedOrders = findMatchingOrders();
        if (matchedOrders != null){
            // Process transaction in the transactionService
            transactionService.processTransaction(matchedOrders[0], matchedOrders[1]);
            removeMatchedOrders(matchedOrders[0], matchedOrders[1]);
        }
    }

    /**
     * This method iterates over the buyOrders and sellOrders lists to find matching orders.
     * @return Order[]
     */
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

    /**
     * This method receives two orders and checks if they match.
     * It checks if the cryptoCurrency is the same, the amount is the same,
     * the users are different, and the maxPrice of the buyOrder is greater than or equal to the minPrice of the sellOrder.
     * @param buyOrder
     * @param sellOrder
     * @return boolean
     */
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

    /**
     * This method receives two orders and removes them from the buyOrders and sellOrders lists.
     * @param buyOrder
     * @param sellOrder
     */
    private void removeMatchedOrders(Order buyOrder, Order sellOrder){
        buyOrders.remove(buyOrder);
        sellOrders.remove(sellOrder);
    }
}
