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

    public void depositCrypto(CryptoCurrency cryptoCurrency, BigDecimal amount) {
        if(cryptoBalances.containsKey(cryptoCurrency)){
            cryptoBalances.put(cryptoCurrency, cryptoBalances.get(cryptoCurrency).add(amount));
        } else{
            cryptoBalances.put(cryptoCurrency, amount);
        }
    }

    public void withdrawCrypto(CryptoCurrency cryptoCurrency, BigDecimal amount) {
        if(cryptoBalances.containsKey(cryptoCurrency) && cryptoBalances.get(cryptoCurrency).compareTo(amount) >= 0){
            cryptoBalances.put(cryptoCurrency, cryptoBalances.get(cryptoCurrency).subtract(amount));
        } else{
            throw new InsufficientFundsException();
        }
    }

    /**
     * Returns the balance of the wallet in a human-readable format
     * @return a string with the balance of the wallet
     */
    public String viewBalance() {
        StringBuilder balance = new StringBuilder("Fiat balance: ").append(fiatBalance).append("\nCrypto Balances:\n");
        cryptoBalances.forEach((crypto, amount) ->
                balance.append(crypto.getSymbol()).append(": ").append(amount).append("\n")
        );
        return balance.toString();
    }

}
