package com.globant.controllers;

import com.globant.exceptions.InsufficientFundsException;
import com.globant.exceptions.UnknowCryptoCurrencyException;
import com.globant.models.BuyOrder;
import com.globant.models.CryptoCurrency;
import com.globant.models.SellOrder;
import com.globant.service.OrderBook;
import com.globant.service.SystemExchangeService;
import com.globant.service.UserService;
import com.globant.service.WalletService;
import com.globant.views.ConsoleView;

import java.math.BigDecimal;

class PlaceSellOrderController {
    private final ConsoleView view;
    private final WalletService walletService;
    private final UserService userService;
    private final SystemExchangeService systemExchangeService;
    private final OrderBook orderBook;

    public PlaceSellOrderController(ConsoleView view, UserService userService, WalletService walletService,
                                    SystemExchangeService systemExchangeService, OrderBook orderBook){
        this.view = view;
        this.userService = userService;
        this.walletService = walletService;
        this.systemExchangeService = systemExchangeService;
        this.orderBook = orderBook;
    }

    public void execute(){
        view.showInfo(systemExchangeService.getAvailableCryptosAndMarketPrice());
        String crypto = view.getCryptoCurrencySymbol().toUpperCase();
        try{
            CryptoCurrency cryptoCurrency = systemExchangeService.getCryptoCurrencyBySymbol(crypto);
            BigDecimal amount = view.getAmount("Enter the amount of Crypto:");
            walletService.cryptosFoundsValidation(cryptoCurrency,amount);
            walletService.withdrawCrypto(cryptoCurrency,amount);
            BigDecimal minPrice = view.getAmount("Enter the minimum price:");
            orderBook.addOrder(new SellOrder(cryptoCurrency,amount,userService.getCurrentUser(),minPrice));
            view.showInfo("Sell Order placed successfully");
        }catch (UnknowCryptoCurrencyException e){
            view.showError("Unknown crypto currency. Please try again.");
        }catch (InsufficientFundsException e){
            view.showError(e.getMessage());
        }

    }

}
