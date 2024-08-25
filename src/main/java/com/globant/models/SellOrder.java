package com.globant.models;

import java.math.BigDecimal;

public class SellOrder extends Order{
    private BigDecimal minPrice;

    public SellOrder(CryptoCurrency cryptoCurrency, BigDecimal amount, BigDecimal minPrice) {
        super(cryptoCurrency, amount);
        this.minPrice = minPrice;
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
        return super.toString() + "\nminPrice=" + minPrice;
    }

}
