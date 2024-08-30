package com.globant.controllers;

import com.globant.service.OrderBook;
import com.globant.service.SystemExchangeService;
import com.globant.service.TransactionService;
import com.globant.service.UserService;
import com.globant.views.ConsoleView;

public class RootController {
    private final UserService userService;
    private AuthController authController;
    private UserOperationsController userOperationsController;

    public RootController(ConsoleView view, UserService userService, SystemExchangeService systemExchangeService, TransactionService transactionService, OrderBook orderBook) {
        this.userService = userService;
        this.authController = new AuthController(view, userService);
        this.userOperationsController = new UserOperationsController(view, userService, systemExchangeService, transactionService, orderBook);
    }

    public void run() {
        while (true) {
            if (userService.isLoggedIn()) {
                userOperationsController.execute();
            } else {
                authController.execute();
            }
        }
    }
}
