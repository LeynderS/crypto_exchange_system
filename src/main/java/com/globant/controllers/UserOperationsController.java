package com.globant.controllers;

import com.globant.service.*;
import com.globant.views.ConsoleView;

/**
 * This class is responsible for handling the user operations.
 */
class UserOperationsController {
    private final ConsoleView view;
    private final UserService userService;
    private final DepositController depositController;
    private final CheckWalletController checkWalletController;
    private final BuyExchangeController buyExchangeController;
    private final PlaceBuyOrderController placeBuyOrderController;
    private final PlaceSellOrderController placeSellOrderController;
    private final TransactionController transactionController;
    private final CancelOrderController cancelOrderController;

    public UserOperationsController(ConsoleView view, UserService userService, WalletService walletService,
                                    SystemExchangeService systemExchangeService, TransactionService transactionService,
                                    OrderBook orderBook){
        this.view = view;
        this.userService = userService;
        this.depositController = new DepositController(view, userService, walletService);
        this.checkWalletController = new CheckWalletController(view, userService, walletService);
        this.buyExchangeController = new BuyExchangeController(view, userService, walletService, systemExchangeService);
        systemExchangeService.setObserver(buyExchangeController); // Setting the buyExchangeController as observer of the PriceFluctuation in the SystemExchangeService
        this.placeBuyOrderController = new PlaceBuyOrderController(view, userService, walletService, systemExchangeService, orderBook);
        this.placeSellOrderController = new PlaceSellOrderController(view, userService, walletService, systemExchangeService, orderBook);
        this.transactionController = new TransactionController(view, userService, transactionService);
        this.cancelOrderController = new CancelOrderController(view, userService, orderBook);
    }

    public void execute(){
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
                    // An extra feature to cancel a pending order
                    cancelOrderController.execute();
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
