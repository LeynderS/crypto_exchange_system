package com.globant.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class Order {
    private String orderId;
    private CryptoCurrency cryptoCurrency;
    private BigDecimal amount;
    private LocalDateTime date;

    public Order(CryptoCurrency cryptoCurrency, BigDecimal amount) {
        this.orderId = "ORDER-" + java.util.UUID.randomUUID().toString();
        this.cryptoCurrency = cryptoCurrency;
        this.amount = amount;
        this.date = LocalDateTime.now();
    }

    public abstract void placeOrder();
    public abstract void executeOrder();
    public abstract void cancelOrder();

    @Override
    public String toString() {
        return "Order:" +
                "\norderId= " + orderId +
                "\ncryptoCurrency=" + cryptoCurrency +
                "\namount=" + amount +
                "\ndate=" + date;
    }
}
