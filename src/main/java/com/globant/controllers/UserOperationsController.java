package com.globant.controllers;

import com.globant.service.UserService;
import com.globant.views.ConsoleView;

class UserOperationsController {
    private final ConsoleView view;
    private final UserService userService;

    public UserOperationsController(ConsoleView view, UserService userService) {
        this.view = view;
        this.userService = userService;
    }

    public void execute(){
        while(true){
            int choice = view.getUserOperationsChoice(userService.getCurrentUser().getName());
            switch (choice){
                case 1:
                    view.showSuccessMessage("Depositing Fiat Money");
                    break;
                case 2:
                    view.showSuccessMessage("Checking Wallet Balances");
                    break;
                case 3:
                    view.showSuccessMessage("Buying Crypto From Exchange");
                    break;
                case 4:
                    view.showSuccessMessage("Placing Buy Order");
                    break;
                case 5:
                    view.showSuccessMessage("Placing Sell Order");
                    break;
                case 6:
                    view.showSuccessMessage("Checking Open Orders");
                    break;
                case 7:
                    view.showSuccessMessage("Checking Transaction History");
                    break;
                case 8:
                    userService.logout();
                    view.showSuccessMessage("Logout successful");
                    return;
                default:
                    view.showError("Invalid option. Please try again.");
            }
        }
    }
}
