package com.globant.repositories;

import com.globant.exceptions.NoTransactionsFoundException;
import com.globant.models.User;

import java.util.*;

import com.globant.models.Transaction;

public class TransactionRepository {
    private final Map<User, List<Transaction>> transactions = new HashMap<>();

    public void save(User user, Transaction transaction) {
        if (transactions.containsKey(user)) {
            List<Transaction> userTransactions = transactions.get(user);
            userTransactions.add(transaction);
            transactions.put(user, userTransactions);
        }else {
            transactions.put(user, new ArrayList<>(List.of(transaction)));
        }
    }

    public List<Transaction> getTransactions(User user) {
        return Optional.ofNullable(transactions.get(user))
                .map(List::copyOf).orElseThrow(NoTransactionsFoundException::new);
}
}
