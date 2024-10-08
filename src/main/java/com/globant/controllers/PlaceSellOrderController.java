package com.globant.controllers;

import com.globant.exceptions.InsufficientFundsException;
import com.globant.exceptions.UnknownCryptoCurrencyException;
import com.globant.models.CryptoCurrency;
import com.globant.models.SellOrder;
import com.globant.service.OrderBook;
import com.globant.service.SystemExchangeService;
import com.globant.service.UserService;
import com.globant.service.WalletService;
import com.globant.views.ConsoleView;

import java.math.BigDecimal;

/**
 * Controller class to place a sell order
 */
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
        // First show the user the available cryptos and their market price in the Exchange
        view.showInfo(systemExchangeService.getAvailableCryptosAndMarketPrice());
        String crypto = view.getCryptoCurrencySymbol().toUpperCase();
        try{
            CryptoCurrency cryptoCurrency = systemExchangeService.getCryptoCurrencyBySymbol(crypto);
            BigDecimal amount = view.getAmount("Enter the amount of Crypto:");
            // Withdraw the amount of Crypto from the user's wallet to avoid future transaction conflicts
            walletService.withdrawCrypto(userService.getCurrentUser().getWallet(), cryptoCurrency,amount);
            BigDecimal minPrice = view.getAmount("Enter the minimum price:");
            // Add the sell order to the order book
            orderBook.addOrder(new SellOrder(cryptoCurrency,amount,userService.getCurrentUser(),minPrice));
            view.showInfo("Sell Order placed successfully");
        }catch (UnknownCryptoCurrencyException e){
            view.showError("Unknown crypto currency. Please try again.");
        }catch (InsufficientFundsException e){
            view.showError(e.getMessage());
        }

    }

}
