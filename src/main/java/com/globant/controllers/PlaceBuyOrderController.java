package com.globant.controllers;

import com.globant.exceptions.InsufficientFundsException;
import com.globant.exceptions.UnknownCryptoCurrencyException;
import com.globant.models.BuyOrder;
import com.globant.models.CryptoCurrency;
import com.globant.service.OrderBook;
import com.globant.service.SystemExchangeService;
import com.globant.service.UserService;
import com.globant.service.WalletService;
import com.globant.views.ConsoleView;

import java.math.BigDecimal;

/**
 * Controller class to place a buy order
 */
class PlaceBuyOrderController {
    private final ConsoleView view;
    private final WalletService walletService;
    private final UserService userService;
    private final SystemExchangeService systemExchangeService;
    private final OrderBook orderBook;

    public PlaceBuyOrderController(ConsoleView view, UserService userService, WalletService walletService,
                                   SystemExchangeService systemExchangeService, OrderBook orderBook){
        this.view = view;
        this.userService = userService;
        this.walletService = walletService;
        this.systemExchangeService = systemExchangeService;
        this.orderBook = orderBook;
    }

    /**
     * Method to execute the controller
     */
    public void execute(){
        // First show the user the available cryptos and their market price in the exchange
        view.showInfo(systemExchangeService.getAvailableCryptosAndMarketPrice());
        String crypto = view.getCryptoCurrencySymbol().toUpperCase();
        try{
            CryptoCurrency cryptoCurrency = systemExchangeService.getCryptoCurrencyBySymbol(crypto);
            BigDecimal amount = view.getAmount("Enter the amount of Crypto:");
            BigDecimal maxPrice = view.getAmount("Enter the maximum price:");
            // Withdraw the maxPrice from the User Wallet to avoid future transactions conflicts
            walletService.withdrawFiat(userService.getCurrentUser().getWallet(), maxPrice);
            // Add the buy order to the order book
            orderBook.addOrder(new BuyOrder(cryptoCurrency,amount,userService.getCurrentUser(),maxPrice));
            view.showInfo("Buy Order placed successfully");
        }catch (UnknownCryptoCurrencyException e){
            view.showError("Unknown crypto currency. Please try again.");
        }catch (InsufficientFundsException e){
            view.showError(e.getMessage());
        }

    }

}
