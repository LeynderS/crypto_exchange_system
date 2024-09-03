package com.globant.controllers;

import com.globant.exceptions.UserNotFoundException;
import com.globant.models.Order;
import com.globant.service.OrderBook;
import com.globant.service.UserService;
import com.globant.views.ConsoleView;

import java.util.List;

/**
 * This class is responsible for handling the cancel order process.
 */
class CancelOrderController {
    private final ConsoleView view;
    private final OrderBook orderBook;
    private final UserService userService;

    public CancelOrderController(ConsoleView view, UserService userService, OrderBook orderBook){
        this.view = view;
        this.orderBook = orderBook;
        this.userService = userService;
    }

    public void execute(){
        try{
            List<Order> userOrders = orderBook.getUserOrders(userService.getCurrentUser()); // Get the user orders
            StringBuilder formattedOrders = new StringBuilder();

            int i = 1;
            // Format the orders to show them to the user
            for (Order order : userOrders) {
                formattedOrders.append(i).append(".-").append(order.toString()).append("\n------------------------\n");
                i++;
            }
            view.showInfo(formattedOrders.toString());
            int index = view.getOrderNumber(); // Get the order number to cancel
            if (index > userOrders.size() || index < 1){
                view.showError("Invalid order number.");
                return;
            }
            if(view.getConfirmation("Are you sure you want to cancel this order?")){
                orderBook.cancelUserOrder(userService.getCurrentUser(), index - 1);
                view.showInfo("Order cancelled successfully.");
            }else{
                view.showInfo("Order not cancelled.");
                return;
            }
        }catch(UserNotFoundException e){
            view.showError("You don't have any orders.");
        }
    }
}
