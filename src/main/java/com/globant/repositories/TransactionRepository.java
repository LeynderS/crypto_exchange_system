package com.globant.repositories;

import com.globant.exceptions.NoTransactionsFoundException;
import com.globant.models.User;

import java.util.*;

import com.globant.models.Transaction;

/**
 * In-memory repository for transactions
 */
public class TransactionRepository {
    // Map to store transactions by user
    private final Map<User, List<Transaction>> transactions = new HashMap<>();

    /**
     * Saves a transaction in the repository
     *
     * @param user the user to save
     * @param transaction the transaction to save
     */
    public void save(User user, Transaction transaction) {
        if (transactions.containsKey(user)) {
            List<Transaction> userTransactions = transactions.get(user);
            userTransactions.add(transaction);
            transactions.put(user, userTransactions);
        }else {
            transactions.put(user, new ArrayList<>(List.of(transaction)));
        }
    }

    /**
     * Retrieves all transactions for a user
     *
     * @param user the user to search for
     * @return the transactions for the given user
     * @throws NoTransactionsFoundException if no transactions are found
     */
    public List<Transaction> getTransactions(User user) {
        return Optional.ofNullable(transactions.get(user))
                .map(List::copyOf).orElseThrow(NoTransactionsFoundException::new);
}
}
