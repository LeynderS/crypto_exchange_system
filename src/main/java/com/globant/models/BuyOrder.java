package com.globant.models;

import java.math.BigDecimal;

public class BuyOrder extends Order{
    private BigDecimal maxPrice;

    public BuyOrder(CryptoCurrency cryptoCurrency, BigDecimal amount, BigDecimal maxPrice) {
        super(cryptoCurrency, amount);
        this.maxPrice = maxPrice;
    }


    @Override
    public void placeOrder() {

    }

    @Override
    public void executeOrder() {

    }

    @Override
    public void cancelOrder() {

    }

    @Override
    public String toString() {
        return super.toString() + "\nmaxPrice=" + maxPrice;
    }
}
