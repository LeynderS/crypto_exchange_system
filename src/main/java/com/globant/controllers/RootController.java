package com.globant.controllers;

import com.globant.service.SystemExchangeService;
import com.globant.service.TransactionService;
import com.globant.service.UserService;
import com.globant.views.ConsoleView;

public class RootController {
    private final ConsoleView view;
    private final UserService userService;
    private AuthController authController;
    private UserOperationsController userOperationsController;

    public RootController(ConsoleView view, UserService userService, SystemExchangeService systemExchangeService, TransactionService transactionService) {
        this.view = view;
        this.userService = userService;
        this.authController = new AuthController(view, userService);
        this.userOperationsController = new UserOperationsController(view, userService, systemExchangeService, transactionService);
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
