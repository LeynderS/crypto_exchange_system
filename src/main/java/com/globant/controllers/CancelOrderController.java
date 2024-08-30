package com.globant.controllers;

import com.globant.exceptions.UserNotFoundException;
import com.globant.models.Order;
import com.globant.models.Transaction;
import com.globant.service.OrderBook;
import com.globant.service.UserService;
import com.globant.views.ConsoleView;

import java.util.List;

class CancelOrderController {
    private final ConsoleView view;
    private final OrderBook orderBook;
    private final UserService userService;

    public CancelOrderController(ConsoleView view, UserService userService) {
        this.view = view;
        this.orderBook = OrderBook.getInstance();
        this.userService = userService;
    }

    public void execute(){
        try{
            int i = 1;
            List<Order> userOrders = orderBook.getUserOrders(userService.getCurrentUser());
            StringBuilder formattedOrders = new StringBuilder();

            for (Order order : userOrders) {
                formattedOrders.append(i).append(".-").append(order.toString()).append("\n------------------------\n");
                i++;
            }
            view.showInfo(formattedOrders.toString());
            int index = view.getOrderNumber();
            if (index > userOrders.size()){
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
