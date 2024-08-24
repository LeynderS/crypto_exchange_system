package com.globant.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class Transaction {
    private String transactionId;
    private CryptoCurrency cryptoCurrency;
    private BigDecimal amount;
    private BigDecimal price;
    private TransactionType type;
    private LocalDateTime timestamp;

    public Transaction(CryptoCurrency cryptoCurrency, BigDecimal amount, BigDecimal price, TransactionType type) {
        this.transactionId = "TX-" + java.util.UUID.randomUUID().toString();
        this.cryptoCurrency = cryptoCurrency;
        this.amount = amount;
        this.price = price;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }

}
