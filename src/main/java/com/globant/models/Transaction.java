package com.globant.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


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

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder().append("Transaction:\n")
                .append("transactionId= ").append(transactionId)
                .append("\ncryptoCurrency= ").append(cryptoCurrency)
                .append("\namount= ").append(amount)
                .append("\nprice= ").append(price).append(" $")
                .append("\ntype= ").append(type)
                .append("\ntimestamp= ").append(timestamp.format(formatter));
        return sb.toString();
    }
}
