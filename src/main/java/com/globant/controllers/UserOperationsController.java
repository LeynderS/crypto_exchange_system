package com.globant.controllers;

import com.globant.service.SystemExchangeService;
import com.globant.service.TransactionService;
import com.globant.service.UserService;
import com.globant.service.WalletService;
import com.globant.views.ConsoleView;

class UserOperationsController {
    private final ConsoleView view;
    private final UserService userService;
    private WalletService walletService;
    private DepositController depositController;
    private CheckWalletController checkWalletController;
    private BuyExchangeController buyExchangeController;
    private PlaceBuyOrderController placeBuyOrderController;
    private PlaceSellOrderController placeSellOrderController;
    private TransactionController transactionController;

    public UserOperationsController(ConsoleView view, UserService userService,
                                    SystemExchangeService systemExchangeService, TransactionService transactionService) {
        this.view = view;
        this.userService = userService;
        this.walletService = new WalletService();
        this.depositController = new DepositController(view, walletService);
        this.checkWalletController = new CheckWalletController(view, walletService);
        this.buyExchangeController = new BuyExchangeController(view, walletService, systemExchangeService);
        this.placeBuyOrderController = new PlaceBuyOrderController(view, userService, walletService, systemExchangeService);
        this.placeSellOrderController = new PlaceSellOrderController(view, userService, walletService, systemExchangeService);
        this.transactionController = new TransactionController(view, userService, transactionService);
    }

    private void updateUserSession(){
        walletService.setWallet(userService.getCurrentUser().getWallet());
    }

    public void execute(){
        updateUserSession();
        while(true){
            int choice = view.getUserOperationsChoice(userService.getCurrentUser().getName());
            switch (choice){
                case 1:
                    depositController.execute();
                    break;
                case 2:
                    checkWalletController.execute();
                    break;
                case 3:
                    buyExchangeController.execute();
                    break;
                case 4:
                    placeBuyOrderController.execute();
                    break;
                case 5:
                    placeSellOrderController.execute();
                    break;
                case 6:
                    view.showSuccessMessage("Checking Open Orders");
                    break;
                case 7:
                    transactionController.execute();
                    break;
                case 8:
                    userService.logout();
                    view.showSuccessMessage("Logout successful");
                    return;
                default:
                    view.showError("Invalid option. Please try again.");
            }
        }
    }
}
