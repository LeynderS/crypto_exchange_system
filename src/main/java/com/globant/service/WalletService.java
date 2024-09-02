package com.globant.service;

import com.globant.models.CryptoCurrency;
import com.globant.models.Wallet;

import java.math.BigDecimal;

/**
 * Service class for wallets.
 */
public class WalletService {

    // Deposit fiat money to a wallet
    public void depositFiat(Wallet wallet, BigDecimal amount) {
        wallet.depositFiat(amount);
    }

    // Withdraw fiat money from a wallet
    public void withdrawFiat(Wallet wallet, BigDecimal amount) {
        wallet.withdrawFiat(amount);
    }

    // Deposit cryptocurrency to a wallet
    public void depositCrypto(Wallet wallet, CryptoCurrency cryptoCurrency, BigDecimal amount) {
        wallet.depositCrypto(cryptoCurrency, amount);
    }

    // Withdraw cryptocurrency from a wallet
    public void withdrawCrypto(Wallet wallet, CryptoCurrency cryptoCurrency, BigDecimal amount) {
        wallet.withdrawCrypto(cryptoCurrency, amount);
    }

    // View the balance of a wallet
    public String viewBalance(Wallet wallet) {
        return wallet.viewBalance();
    }

}
