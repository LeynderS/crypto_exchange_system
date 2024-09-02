package com.globant.controllers;

import com.globant.service.UserService;
import com.globant.service.WalletService;
import com.globant.views.ConsoleView;

/**
 * Controller class to check the wallet balance
 */
class CheckWalletController {
    private final ConsoleView view;
    private final UserService userService;
    private final WalletService walletService;
    public CheckWalletController(ConsoleView view, UserService userService, WalletService walletService) {
        this.view = view;
        this.userService = userService;
        this.walletService = walletService;
    }

    public void execute(){
        // Show the balance of the current user
        view.showInfo(walletService.viewBalance(userService.getCurrentUser().getWallet()));
    }
}
