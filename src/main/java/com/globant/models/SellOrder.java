package com.globant.models;

import java.math.BigDecimal;

public class SellOrder extends Order{
    private BigDecimal minPrice;

    public SellOrder(CryptoCurrency cryptoCurrency, BigDecimal amount, User user,BigDecimal minPrice) {
        super(cryptoCurrency, amount, user);
        this.minPrice = minPrice;
    }
    public BigDecimal getMinPrice() {
        return minPrice;
    }
    @Override
    public String toString() {
        return super.toString() + "\nminPrice= " + minPrice + " $";
    }

}
