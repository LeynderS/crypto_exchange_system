package com.globant.controllers;

import com.globant.service.UserService;
import com.globant.views.ConsoleView;

public class RootController {
    private final ConsoleView view;
    private final UserService userService;
    private AuthController authController;
    private UserOperationsController userOperationsController;

    public RootController(ConsoleView view, UserService userService) {
        this.view = view;
        this.userService = userService;
        this.authController = new AuthController(view, userService);
    }

    public void run() {
        while (true) {
            if (userService.isLoggedIn()) {
                userOperationsController = new UserOperationsController(view, userService);
                userOperationsController.execute();
            } else {
                authController.execute();
            }
        }
    }
}
