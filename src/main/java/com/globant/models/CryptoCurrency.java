package com.globant.models;

import java.math.BigDecimal;

public class CryptoCurrency {
    String symbol;
    String name;
    BigDecimal marketPrice;

    public CryptoCurrency(String symbol, String name, BigDecimal marketPrice) {
        this.symbol = symbol;
        this.name = name;
        this.marketPrice = marketPrice;
    }

    public void updateMarketPrice(BigDecimal newPrice) {
        this.marketPrice = newPrice;
    }

    public BigDecimal getMarketPrice() {
        return this.marketPrice;
    }
}
