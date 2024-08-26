package com.globant;

import com.globant.controllers.RootController;
import com.globant.repositories.CryptoCurrencyRepository;
import com.globant.repositories.InMemoryUserRepository;
import com.globant.service.SystemExchangeService;
import com.globant.service.UserService;
import com.globant.views.ConsoleView;

public class CryptoExchangeApp {
    public static void main(String[] args) {
        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        CryptoCurrencyRepository cryptoCurrencyRepository = new CryptoCurrencyRepository();
        SystemExchangeService systemExchangeService = new SystemExchangeService(cryptoCurrencyRepository);
        UserService userService = new UserService(userRepository);
        ConsoleView view = new ConsoleView();
        RootController controller = new RootController(view, userService, systemExchangeService);
        controller.run();
        view.close();

    }
}