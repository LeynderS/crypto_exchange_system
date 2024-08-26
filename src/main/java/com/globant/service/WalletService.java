package com.globant.service;

import com.globant.models.CryptoCurrency;
import com.globant.models.Wallet;

import java.math.BigDecimal;

public class WalletService {
    private Wallet wallet;

    public WalletService(Wallet wallet) {
        this.wallet = wallet;
    }

    public void depositFiat(BigDecimal amount) {
        wallet.depositFiat(amount);
    }

    public void withdrawFiat(BigDecimal amount) {
        wallet.withdrawFiat(amount);
    }

    public void depositCrypto(CryptoCurrency cryptoCurrency, BigDecimal amount) {
        wallet.depositCrypto(cryptoCurrency, amount);
    }

    public void withdrawCrypto(CryptoCurrency cryptoCurrency, BigDecimal amount) {
        wallet.withdrawCrypto(cryptoCurrency, amount);
    }

    public String viewBalance() {
        return wallet.viewBalance();
    }
}
