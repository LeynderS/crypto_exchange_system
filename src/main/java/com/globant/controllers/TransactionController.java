package com.globant.controllers;

import com.globant.exceptions.NoTransactionsFoundException;
import com.globant.models.Transaction;
import com.globant.models.User;
import com.globant.service.TransactionService;
import com.globant.service.UserService;
import com.globant.views.ConsoleView;

import java.util.List;

class TransactionController {
    private final ConsoleView view;
    private final UserService userService;
    private final TransactionService transactionService;

    public TransactionController(ConsoleView view, UserService userService, TransactionService transactionService) {
        this.view = view;
        this.userService = userService;
        this.transactionService = transactionService;
    }

    public void execute() {
        User user = userService.getCurrentUser();
        try {
            List<Transaction> transactions = transactionService.getTransactions(user);
            StringBuilder formattedTransactions = new StringBuilder();

            for (Transaction transaction : transactions) {
                formattedTransactions.append(transaction.toString()).append("\n------------------------\n");
            }

            view.showInfo(formattedTransactions.toString());
        } catch (NoTransactionsFoundException e) {
            view.showError("No transactions at the moment.");
        }
    }

}
