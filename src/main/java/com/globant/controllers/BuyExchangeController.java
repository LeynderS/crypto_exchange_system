package com.globant.controllers;

import com.globant.models.CryptoCurrency;
import com.globant.exceptions.InsufficientFundsException;
import com.globant.service.SystemExchangeService;
import com.globant.exceptions.UnknowCryptoCurrencyException;
import com.globant.service.WalletService;
import com.globant.views.ConsoleView;

import java.math.BigDecimal;

public class BuyExchangeController {
    private final ConsoleView view;
    private final SystemExchangeService systemExchangeService;
    private final WalletService walletService;

    public BuyExchangeController(ConsoleView view, WalletService walletService, SystemExchangeService systemExchangeService) {
        this.view = view;
        this.walletService = walletService;
        this.systemExchangeService = systemExchangeService;
    }
    public void execute() {
        view.showInfo(systemExchangeService.getAvailableCryptosAndMarketPrice());
        try{
            String symbol = view.getCryptoCurrencySymbol();
            BigDecimal amount = view.getAmount();
            systemExchangeService.sufficientCryptos(symbol, amount);
            walletService.withdrawFiat(systemExchangeService.getTotalPrice(symbol, amount));
            CryptoCurrency cryptoCurrency = systemExchangeService.buyCryptoCurrency(symbol, amount);
            walletService.depositCrypto(cryptoCurrency, amount);
            view.showSuccessMessage("Purchase successful");
        }catch (UnknowCryptoCurrencyException e){
            view.showError("Unknown crypto currency. Please try again.");
        }catch (InsufficientFundsException e){
            view.showError(e.getMessage());
        }
    }
}
