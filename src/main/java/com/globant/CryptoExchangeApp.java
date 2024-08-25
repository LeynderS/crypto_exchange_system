package com.globant;

import com.globant.controllers.RootController;
import com.globant.service.InMemoryUserRepository;
import com.globant.service.UserService;
import com.globant.views.ConsoleView;

public class CryptoExchangeApp {
    public static void main(String[] args) {
        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        UserService userService = new UserService(userRepository);
        ConsoleView view = new ConsoleView();
        RootController controller = new RootController(view, userService);
        controller.run();
        view.close();

    }
}