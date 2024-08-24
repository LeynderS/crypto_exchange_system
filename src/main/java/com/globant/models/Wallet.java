package com.globant.models;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Wallet {
    private BigDecimal fiatBalance;
    private Map<CryptoCurrency, BigDecimal> cryptoBalances;

    public Wallet() {
        this.fiatBalance = BigDecimal.ZERO;
        cryptoBalances = new HashMap<>();
    }

    public void depositFiat(BigDecimal amount) {
        fiatBalance = fiatBalance.add(amount);
    }

    public void withdrawFiat(BigDecimal amount) {
        if(fiatBalance.compareTo(amount) >= 0){
            fiatBalance = fiatBalance.subtract(amount);
        } else{
            throw new InsufficientFundsException();
        }
    }

}
