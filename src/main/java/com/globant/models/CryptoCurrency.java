package com.globant.models;

import java.math.BigDecimal;

public class CryptoCurrency {
    String symbol;
    String name;

    public CryptoCurrency(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol + " - " + name + "\n";
    }
}
