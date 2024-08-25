package com.globant.controllers;

import com.globant.views.ConsoleView;
import com.globant.service.UserService;


 class AuthController {
    private final ConsoleView view;
    private final RegisterController registerController;
    private final LoginController loginController;

    public AuthController(ConsoleView view, UserService userService) {
        this.view = view;
        this.registerController = new RegisterController(view, userService);
        this.loginController = new LoginController(view, userService);
    }

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
