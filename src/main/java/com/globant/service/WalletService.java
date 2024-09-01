package com.globant.service;

import com.globant.models.CryptoCurrency;
import com.globant.models.Wallet;

import java.math.BigDecimal;

public class WalletService {

    public void depositFiat(Wallet wallet, BigDecimal amount) {
        wallet.depositFiat(amount);
    }

    public void withdrawFiat(Wallet wallet, BigDecimal amount) {
        wallet.withdrawFiat(amount);
    }

    public void depositCrypto(Wallet wallet, CryptoCurrency cryptoCurrency, BigDecimal amount) {
        wallet.depositCrypto(cryptoCurrency, amount);
    }

    public void withdrawCrypto(Wallet wallet, CryptoCurrency cryptoCurrency, BigDecimal amount) {
        wallet.withdrawCrypto(cryptoCurrency, amount);
    }

    public String viewBalance(Wallet wallet) {
        return wallet.viewBalance();
    }

}
