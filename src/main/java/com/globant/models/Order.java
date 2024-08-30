package com.globant.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder().append("Order:\n")
                .append("orderId= ").append(orderId)
                .append("\nuser= ").append(user)
                .append("\ncryptoCurrency= ").append(cryptoCurrency)
                .append("\namount= ").append(amount)
                .append("\ndate= ").append(date.format(formatter));
        return sb.toString();
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
