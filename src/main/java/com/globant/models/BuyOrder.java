package com.globant.models;

import java.math.BigDecimal;

public class BuyOrder extends Order{
    private BigDecimal maxPrice;

    public BuyOrder(CryptoCurrency cryptoCurrency, BigDecimal amount, User user,BigDecimal maxPrice) {
        super(cryptoCurrency, amount, user);
        this.maxPrice = maxPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }
    @Override
    public String toString() {
        return super.toString() + "\nmaxPrice=" + maxPrice;
    }
}
