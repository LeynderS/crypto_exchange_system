# Crypto Exchange Platform

## Overview

The Crypto Exchange Platform is a Java-based application that facilitates cryptocurrency transactions. It provides a user-friendly interface by console for managing cryptocurrency trades and wallet balances. Users can authenticate, register, and perform various operations related to cryptocurrencies and fiat money.

## Features

- **User Authentication**: Users can register, log in, and manage their accounts.
- **Wallet Management**: Each user has a wallet that holds both fiat money and cryptocurrencies. Users can deposit funds.
- **Crypto Exchange**:
    - **Direct Purchase**: Buy cryptocurrencies at market prices from the exchange.
    - **Price Notifications**: When purchasing cryptocurrencies, users receive notifications if the market price changes during the transaction. This is achieved through the Observer pattern where `BuyExchangeController` observes price fluctuations in `SystemExchangeService`.
- **Buy/Sell Orders**: 
  - Users can place buy or sell orders with specific amounts and prices.
  - **Order Matching**: Place buy or sell orders that match with other users' orders. Orders are matched based on:
    - Same cryptocurrency
    - Different users
    - Equal cryptocurrency amounts
    - Buy price greater than or equal to the sell price
- **Transaction Execution**: Upon a successful match, transactions are processed. If a buy order is placed at a higher price than the sell order, the excess amount is returned to the buyer.
- **Order Cancellation**: Users can cancel pending orders if they need their funds or cryptocurrencies back. This feature helps avoid conflicts and allows users to recover their resources if an order takes too long to match.
- **Wallet Balance and Transaction History**: Users can check their wallet balance and view their transaction history.
- **Session Management**: Users can log out.
- **Exit**: Terminate the application.

## Technologies Used

- **Java**: Core programming language for the application development.
- **Model-View-Controller (MVC)**: To separate concerns and manage user interactions effectively.
- **Observer Pattern**: For real-time notifications about price changes during transactions.
- **Dependency Injection**: Ensures single instances of repositories through constructor injection.

## Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/LeynderS/crypto_exchange_system
    ```
2. **Navigate to src/main/java/com/globant/**:
3. **Run the CryptoExchangeApp.java file**:


## Usage

**Register an Account**:
- When you first run the application, you will be presented with options to either log in or register a new account.
- Choose the registration option and provide the required information to create a new user account.

**Log In**:
- If you already have an account, select the login option.
- Enter your credentials (email and password) to access your account.

**Exit**: The application will terminate when you choose to exit, all the users and their data will be lost.

**Manage Wallet**: Once logged in, you can manage your wallet.

**View Wallet Balances**: Check your current wallet balances to see the amount of fiat money and cryptocurrencies you hold.

**Deposit Fiat**: Add real money to your wallet.

**Buy Cryptocurrencies From Exchange**:
- To purchase cryptocurrencies directly from the exchange:
  - Select the option to buy crypto from exchange.
  - Enter a cryptocurrency symbol from the available list and enter the amount you wish to buy.
  - Confirm the transaction. The system will show the current market price, and if the price changes during the transaction, you will be notified.

**Buy Cryptocurrencies to other Users**:
- To place a buy order:
    - Select the option to place buy order.
    - Specify the cryptocurrency, amount, and the max Price.
    - Your order will be on a waiting list until a match is made.

**Sell Cryptocurrencies**:
- To place a sell order:
    - Select the option to place sell order.
    - Specify the cryptocurrency, amount, and the min price.
    - Your order will be on a waiting list until a match is made.

**Cancel Orders**:
- If you have pending orders that you no longer wish to keep, you can cancel them.
- Canceling an order will return the funds or cryptocurrencies to your wallet if the order hasn't been matched yet.

**View Transaction History**: View your transaction history to review past transactions (buy and sell matched orders).

**Log Out**: To end your session, select the log-out option.
