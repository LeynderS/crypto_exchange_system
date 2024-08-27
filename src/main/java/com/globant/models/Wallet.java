package com.globant.models;

import com.globant.exceptions.InsufficientFundsException;

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
        fiatFoundsValidation(amount);
        fiatBalance = fiatBalance.subtract(amount);
    }

    public void depositCrypto(CryptoCurrency cryptoCurrency, BigDecimal amount) {
        if(cryptoBalances.containsKey(cryptoCurrency)){
            cryptoBalances.put(cryptoCurrency, cryptoBalances.get(cryptoCurrency).add(amount));
        } else{
            cryptoBalances.put(cryptoCurrency, amount);
        }
    }

    public void withdrawCrypto(CryptoCurrency cryptoCurrency, BigDecimal amount) {
        cryptosFoundsValidation(cryptoCurrency, amount);
        cryptoBalances.put(cryptoCurrency, cryptoBalances.get(cryptoCurrency).subtract(amount));
    }

    public void fiatFoundsValidation(BigDecimal amount) {
        if(fiatBalance.compareTo(amount) < 0){
            throw new InsufficientFundsException("Insufficient funds in your wallet");
        }
    }

    public void cryptosFoundsValidation(CryptoCurrency cryptoCurrency, BigDecimal amount) {
        if(!cryptoBalances.containsKey(cryptoCurrency) || cryptoBalances.get(cryptoCurrency).compareTo(amount) < 0){
            throw new InsufficientFundsException("Insufficient cryptos in your wallet");
        }
    }

    /**
     * Returns the balance of the wallet in a human-readable format
     * @return a string with the balance of the wallet
     */
    public String viewBalance() {
        StringBuilder balance = new StringBuilder("Fiat balance: ").append(fiatBalance).append("\nCrypto Balances:\n");
        cryptoBalances.forEach((crypto, amount) ->
                balance.append(crypto.toString()).append(": ").append(amount).append("\n")
        );
        return balance.toString();
    }

}
