package com.globant.controllers;

import com.globant.service.*;
import com.globant.views.ConsoleView;

/**
 * RootController class is the main controller of the application.
 * It is responsible for handling the user's input and
 */
public class RootController {
    private final UserService userService;
    private AuthController authController;
    private UserOperationsController userOperationsController;

    public RootController(ConsoleView view, UserService userService, WalletService walletService, SystemExchangeService systemExchangeService, TransactionService transactionService, OrderBook orderBook) {
        this.userService = userService;
        this.authController = new AuthController(view, userService);
        this.userOperationsController = new UserOperationsController(view, userService, walletService, systemExchangeService, transactionService, orderBook);
    }

    public void run() {
        while (true) {
            // If the user is logged in, the userOperationsController is executed
            // otherwise the authController is executed
            if (userService.isLoggedIn()) {
                userOperationsController.execute();
            } else {
                authController.execute();
            }
        }
    }
}
