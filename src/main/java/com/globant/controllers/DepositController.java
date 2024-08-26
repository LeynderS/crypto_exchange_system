package com.globant.controllers;

import com.globant.service.WalletService;
import com.globant.views.ConsoleView;

import java.math.BigDecimal;

class DepositController {
    private final ConsoleView view;
    private final WalletService walletService;
    public DepositController(ConsoleView view, WalletService walletService) {
        this.view = view;
        this.walletService = walletService;
    }

    public void execute(){
        BigDecimal amount = view.getAmount();
        walletService.depositFiat(amount);
        view.showSuccessMessage("Deposit successful");
    }
}
