package com.globant.controllers;

import com.globant.service.UserService;
import com.globant.service.WalletService;
import com.globant.views.ConsoleView;

import java.math.BigDecimal;

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
        view.showInfo(walletService.viewBalance(userService.getCurrentUser().getWallet()));
    }
}
