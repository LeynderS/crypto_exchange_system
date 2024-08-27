package com.globant.controllers;

import com.globant.service.WalletService;
import com.globant.views.ConsoleView;

import java.math.BigDecimal;

class CheckWalletController {
    private final ConsoleView view;
    private final WalletService walletService;
    public CheckWalletController(ConsoleView view, WalletService walletService) {
        this.view = view;
        this.walletService = walletService;
    }

    public void execute(){
        view.showInfo(walletService.viewBalance());
    }
}
