package com.globant.service;

import com.globant.models.*;
import com.globant.repositories.TransactionRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service class for transactions.
 */
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletService walletService;
    public TransactionService(TransactionRepository transactionRepository, WalletService walletService) {
        this.transactionRepository = transactionRepository;
        this.walletService = walletService;
    }

    /**
     * Saves a transaction in the database.
     * @param user The user that made the transaction.
     * @param cryptoCurrency The cryptocurrency involved in the transaction.
     * @param amount The amount of cryptocurrency involved in the transaction.
     * @param price The price of the cryptocurrency involved in the transaction.
     * @param transactionType The type of transaction.
     */
    private void saveTransaction(User user, CryptoCurrency cryptoCurrency, BigDecimal amount,
                                BigDecimal price, TransactionType transactionType) {
        transactionRepository.save(user, new Transaction(cryptoCurrency, amount, price, transactionType));
    }

    /**
     * Gets the transactions of a user.
     * @param user The user to get the transactions from.
     * @return A list of transactions of the user.
     */
    public List<Transaction> getTransactions(User user) {
        return transactionRepository.getTransactions(user);
    }

    /**
     * Processes a transaction between a buy order and a sell order.
     * This method is called when a buy order is matched with a sell order in OrderBook.
     * @param buyOrder The buy order.
     * @param sellOrder The sell order.
     */
    void processTransaction(Order buyOrder, Order sellOrder){
        CryptoCurrency cryptoCurrency = buyOrder.getCryptoCurrency();
        BigDecimal amount = buyOrder.getAmount();
        BigDecimal maxPrice = ((BuyOrder) buyOrder).getMaxPrice();
        BigDecimal minPrice = ((SellOrder) sellOrder).getMinPrice();
        User buyer = buyOrder.getUser();
        User seller = sellOrder.getUser();
        walletService.depositCrypto(buyer.getWallet(), cryptoCurrency, amount); // Deposit the cryptocurrency to the buyer
        walletService.depositFiat(buyer.getWallet(), maxPrice.subtract(minPrice)); // Deposit the remaining fiat to the buyer
        walletService.depositFiat(seller.getWallet(), minPrice); // Deposit the fiat to the seller
        saveTransaction(buyer, cryptoCurrency, amount, minPrice, TransactionType.BUY); // Save the buy transaction
        saveTransaction(seller, cryptoCurrency, amount, minPrice, TransactionType.SELL); // Save the sell transaction
    }
}
