package com.globant.controllers;

import com.globant.models.CryptoCurrency;
import com.globant.exceptions.InsufficientFundsException;
import com.globant.service.PriceObserver;
import com.globant.service.SystemExchangeService;
import com.globant.exceptions.UnknownCryptoCurrencyException;
import com.globant.service.UserService;
import com.globant.service.WalletService;
import com.globant.views.ConsoleView;

import java.math.BigDecimal;

/**
 * Controller class for buying crypto currency from the exchange.
 * Implements PriceObserver interface to be able to observe price changes in the exchange.
 */
class BuyExchangeController implements PriceObserver {
    private final ConsoleView view;
    private final UserService userService;
    private final SystemExchangeService systemExchangeService;
    private final WalletService walletService;
    private Boolean priceHasChanged;

    public BuyExchangeController(ConsoleView view, UserService userService, WalletService walletService, SystemExchangeService systemExchangeService) {
        this.view = view;
        this.userService = userService;
        this.walletService = walletService;
        this.systemExchangeService = systemExchangeService;
    }

    /**
     * Method to execute the buy operation.
     */
    public void execute() {
        priceHasChanged = false;
        view.showInfo(systemExchangeService.getAvailableCryptosAndMarketPrice()); // Show available cryptos and market price
        try{
            String symbol = view.getCryptoCurrencySymbol().toUpperCase();
            CryptoCurrency cryptoCurrency = systemExchangeService.getCryptoCurrencyBySymbol(symbol);
            BigDecimal amount = view.getAmount("Enter the amount of Crypto:");
            systemExchangeService.sufficientCryptosInExchangeVal(cryptoCurrency, amount);
            if(priceHasChanged){
                view.showInfo("Price has changed."); // Notify user that price has changed
                view.showInfo(systemExchangeService.getAvailableCryptosAndMarketPrice());
                Boolean confirm = view.getConfirmation("Do you want to continue with the purchase?");
                if(!confirm){
                    view.showInfo("Purchase cancelled."); // Notify user that purchase has been cancelled
                    priceHasChanged = false;
                    return;
                }
            }
            BigDecimal totalPrice = systemExchangeService.getTotalPrice(cryptoCurrency, amount); // Calculate total price
            walletService.withdrawFiat(userService.getCurrentUser().getWallet(), totalPrice);
            systemExchangeService.buyCryptoCurrency(cryptoCurrency, amount);
            walletService.depositCrypto(userService.getCurrentUser().getWallet(), cryptoCurrency, amount);
            view.showSuccessMessage("Purchase successful");
        }catch (UnknownCryptoCurrencyException e){
            view.showError("Unknown crypto currency. Please try again.");
        }catch (InsufficientFundsException e){
            view.showError(e.getMessage()); // Handle insufficient funds exception
        }
    }

    @Override
    public void priceChanged() {
        priceHasChanged = true;
    }
}
