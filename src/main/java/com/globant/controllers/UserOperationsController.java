package com.globant.controllers;

import com.globant.service.UserService;
import com.globant.service.WalletService;
import com.globant.views.ConsoleView;

class UserOperationsController {
    private final ConsoleView view;
    private final UserService userService;
    private WalletService walletService;
    private DepositController depositController;
    private CheckWalletController checkWalletController;

    public UserOperationsController(ConsoleView view, UserService userService) {
        this.view = view;
        this.userService = userService;
    }

    private void updateUserSession(){
        this.walletService = new WalletService(userService.getCurrentUser().getWallet());
        this.depositController = new DepositController(view, walletService);
        this.checkWalletController = new CheckWalletController(view, walletService);
    }

    public void execute(){
        updateUserSession();
        while(true){
            int choice = view.getUserOperationsChoice(userService.getCurrentUser().getName());
            switch (choice){
                case 1:
                    depositController.execute();
                    break;
                case 2:
                    checkWalletController.execute();
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
