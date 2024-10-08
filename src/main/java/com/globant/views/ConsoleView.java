package com.globant.views;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class that handles the console view of the application.
 */
public class ConsoleView {
    private static final String ANSI_RED = "\u001B[31m"; // ANSI escape code for red color
    private static final String ANSI_RESET = "\u001B[0m"; // ANSI escape code to reset color
    private static final String ANSI_GREEN = "\u001B[32m"; // ANSI escape code for green color
    private static final String ANSI_BLUE = "\u001B[34m"; // ANSI escape code for blue color

    private final Scanner scanner = new Scanner(System.in);
    private static final int INVALID_CHOICE = -1;

    // All about Login/Sigup
    public int getChoice(){
        System.out.println("Choose an option to continue:");
        System.out.println("1. LogIn");
        System.out.println("2. SignUp");
        System.out.println("3. Exit");
        String choice = scanner.nextLine();
        try{
            return Integer.parseInt(choice);
        }catch(NumberFormatException e){
            return INVALID_CHOICE;
        }
    }
    public String getNameInput(){
        System.out.println("Enter your name:");
        return scanner.nextLine();
    }
    public String getEmailInput(){
        System.out.println("Enter your email:");
        return scanner.nextLine();
    }

    public String getPasswordInput(){
        System.out.println("Enter your password:");
        return scanner.nextLine();
    }

    public String getPasswordConfirmation(){
        System.out.println("Confirm your password:");
        return scanner.nextLine();
    }

    // All about User Operations
    public int getUserOperationsChoice(String name) {
        System.out.printf("Welcome %s to the CryptoCurrency Exchange\n", name);
        System.out.println("1. Deposit Fiat Money");
        System.out.println("2. Checking Wallet Balances");
        System.out.println("3. Buy Crypto From Exchange");
        System.out.println("4. Place Buy Order");
        System.out.println("5. Place Sell Order");
        System.out.println("6. Cancel Open Orders");
        System.out.println("7. Check Transaction History");
        System.out.println("8. Logout");
        String choice = scanner.nextLine();
        try {
            return Integer.parseInt(choice);
        } catch (NumberFormatException e) {
            return INVALID_CHOICE;
        }
    }

    // All about Wallet
    public BigDecimal getAmount(String message) {
        System.out.println(message);
        try {
            BigDecimal amount = scanner.nextBigDecimal();
            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                throw new InputMismatchException();
            }
            scanner.nextLine();
            return amount;
        } catch (InputMismatchException e) {
            showError("Invalid amount. Please try again.");
            scanner.nextLine();
            return getAmount(message);
        }
    }

    public String getCryptoCurrencySymbol() {
        System.out.println("Enter the symbol of the cryptocurrency:");
        return scanner.nextLine();
    }

    public Boolean getConfirmation(String message) {
        System.out.println(message);
        System.out.println("1. Yes");
        System.out.println("2. No");
        String choice = scanner.nextLine();
        return choice.equals("1");
    }

    public int getOrderNumber(){
        System.out.println("Enter the order number to cancel:");
        String choice = scanner.nextLine();
        try {
            return Integer.parseInt(choice);
        } catch (NumberFormatException e) {
            return INVALID_CHOICE;
        }
    }

    public void showError(String errorMessage) {
        System.out.println(ANSI_RED + errorMessage + ANSI_RESET);
    }

    public void showInfo(String message) {
        System.out.println(ANSI_BLUE + message + ANSI_RESET);
    }

    public void showSuccessMessage(String message) {
        System.out.println(ANSI_GREEN + message + ANSI_RESET);
    }

    public void close(){
        scanner.close();
    }
}
