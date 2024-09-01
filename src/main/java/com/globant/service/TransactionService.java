package com.globant.service;

import com.globant.models.*;
import com.globant.repositories.TransactionRepository;

import java.math.BigDecimal;
import java.util.List;

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletService walletService;
    public TransactionService(TransactionRepository transactionRepository, WalletService walletService) {
        this.transactionRepository = transactionRepository;
        this.walletService = walletService;
    }

    private void saveTransaction(User user, CryptoCurrency cryptoCurrency, BigDecimal amount,
                                BigDecimal price, TransactionType transactionType) {
        transactionRepository.save(user, new Transaction(cryptoCurrency, amount, price, transactionType));
    }

    public List<Transaction> getTransactions(User user) {
        return transactionRepository.getTransactions(user);
    }

    void processTransaction(Order buyOrder, Order sellOrder){
        CryptoCurrency cryptoCurrency = buyOrder.getCryptoCurrency();
        BigDecimal amount = buyOrder.getAmount();
        BigDecimal maxPrice = ((BuyOrder) buyOrder).getMaxPrice();
        BigDecimal minPrice = ((SellOrder) sellOrder).getMinPrice();
        User buyer = buyOrder.getUser();
        User seller = sellOrder.getUser();
        walletService.depositCrypto(buyer.getWallet(), cryptoCurrency, amount);
        walletService.depositFiat(buyer.getWallet(), maxPrice.subtract(minPrice));
        walletService.depositFiat(seller.getWallet(), minPrice);
        saveTransaction(buyer, cryptoCurrency, amount, minPrice, TransactionType.BUY);
        saveTransaction(seller, cryptoCurrency, amount, minPrice, TransactionType.SELL);
    }
}
