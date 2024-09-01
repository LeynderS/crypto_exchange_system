package com.globant;

import com.globant.controllers.RootController;
import com.globant.repositories.CryptoCurrencyRepository;
import com.globant.repositories.InMemoryUserRepository;
import com.globant.repositories.TransactionRepository;
import com.globant.service.*;
import com.globant.views.ConsoleView;

public class CryptoExchangeApp {
    public static void main(String[] args) {
        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        CryptoCurrencyRepository cryptoCurrencyRepository = new CryptoCurrencyRepository();
        TransactionRepository transactionRepository = new TransactionRepository();
        WalletService walletService = new WalletService();
        SystemExchangeService systemExchangeService = new SystemExchangeService(cryptoCurrencyRepository);
        TransactionService transactionService = new TransactionService(transactionRepository, walletService);
        OrderBook orderBook = new OrderBook(transactionService);
        UserService userService = new UserService(userRepository);
        ConsoleView view = new ConsoleView();
        RootController controller = new RootController(view, userService, walletService,systemExchangeService, transactionService, orderBook);
        controller.run();
        view.close();

    }
}