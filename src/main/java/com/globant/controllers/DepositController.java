package com.globant.controllers;

import com.globant.service.UserService;
import com.globant.service.WalletService;
import com.globant.views.ConsoleView;

import java.math.BigDecimal;

/**
 * DepositController class that allows
 * the user to deposit money into their wallet.
 */
class DepositController {
    private final ConsoleView view;
    private final UserService userService;
    private final WalletService walletService;
    public DepositController(ConsoleView view, UserService userService, WalletService walletService) {
        this.view = view;
        this.userService = userService;
        this.walletService = walletService;
    }

    public void execute(){
        BigDecimal amount = view.getAmount("Enter the amount:");
        walletService.depositFiat(userService.getCurrentUser().getWallet(), amount);
        view.showSuccessMessage("Deposit successful");
    }
}
