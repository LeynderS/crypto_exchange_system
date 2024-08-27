package com.globant.service;

import com.globant.models.CryptoCurrency;
import com.globant.models.Wallet;

import java.math.BigDecimal;

public class WalletService {
    private Wallet wallet;

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

    public void fiatFoundsValidation(BigDecimal amount) {
        wallet.fiatFoundsValidation(amount);
    }

    public void cryptosFoundsValidation(CryptoCurrency cryptoCurrency, BigDecimal amount) {
        wallet.cryptosFoundsValidation(cryptoCurrency, amount);
    }

    public String viewBalance() {
        return wallet.viewBalance();
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
