package com.globant.models;


public class CryptoCurrency {
    String symbol;
    String name;

    public CryptoCurrency(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    @Override
    public String toString() {
        return symbol + " - " + name;
    }
}
