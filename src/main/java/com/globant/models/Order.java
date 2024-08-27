package com.globant.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class Order {
    private String orderId;
    private User user;
    private CryptoCurrency cryptoCurrency;
    private BigDecimal amount;
    private LocalDateTime date;

    public Order(CryptoCurrency cryptoCurrency, BigDecimal amount, User user) {
        this.orderId = "ORDER-" + java.util.UUID.randomUUID().toString();
        this.cryptoCurrency = cryptoCurrency;
        this.amount = amount;
        this.user = user;
        this.date = LocalDateTime.now();
    }
    @Override
    public String toString() {
        return "Order:" +
                "\norderId= " + orderId +
                "\nuser=" + user +
                "\ncryptoCurrency=" + cryptoCurrency +
                "\namount=" + amount +
                "\ndate=" + date;
    }

    public User getUser() {
        return user;
    }
    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }
    public BigDecimal getAmount() {
        return amount;
    }
}
