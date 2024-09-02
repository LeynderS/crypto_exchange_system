package com.globant.controllers;

import com.globant.views.ConsoleView;
import com.globant.service.UserService;

/**
 * The AuthController class handles user authentication and registration operations.
 * It provides methods to execute user login, registration, or exit the application.
 */
 class AuthController {
    private final ConsoleView view;
    private final RegisterController registerController;
    private final LoginController loginController;

    public AuthController(ConsoleView view, UserService userService) {
        this.view = view;
        this.registerController = new RegisterController(view, userService);
        this.loginController = new LoginController(view, userService);
    }

    /**
     * Executes the authentication process based on user input.
     * It allows users to either log in, register, or exit the application.
     */
    public void execute(){
        int choice = view.getChoice();
        switch (choice){
            case 1:
                loginController.execute();
                break;
            case 2:
                registerController.execute();
                break;
            case 3:
                view.showSuccessMessage("Exiting application");
                System.exit(0);
            default:
                view.showError("Invalid option. Please try again.");
        }
    }
}
