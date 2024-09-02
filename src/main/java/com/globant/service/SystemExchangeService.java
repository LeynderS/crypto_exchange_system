package com.globant.service;

import com.globant.exceptions.InsufficientFundsException;
import com.globant.models.CryptoCurrency;
import com.globant.repositories.CryptoCurrencyRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class is responsible for managing the exchange of cryptocurrencies.
 * It has a map of cryptocurrencies and their market price, and another map with the availability of each cryptocurrency.
 * It also has a scheduled executor service that will be used to simulate the price fluctuation in one Thread.
 * It also has a method to set an observer that will be notified when the price of a cryptocurrency changes.
 */
public class SystemExchangeService extends Observable {

    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final Map<CryptoCurrency, BigDecimal> cryptosMarketPrice = new HashMap<>();
    private final Map<CryptoCurrency, BigDecimal> cryptosAvailability = new HashMap<>();
    private PriceObserver observer; // observer that will be notified when the price of a cryptocurrency changes

    // scheduled executor service that will be used to simulate the price fluctuation in one Thread
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public SystemExchangeService(CryptoCurrencyRepository cryptoCurrencyRepository) {
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
        cryptosMarketPrice.put(cryptoCurrencyRepository.getCryptoCurrencyBySymbol("BTC"), new BigDecimal("50000"));
        cryptosMarketPrice.put(cryptoCurrencyRepository.getCryptoCurrencyBySymbol("ETH"), new BigDecimal("3000"));
        cryptosAvailability.put(cryptoCurrencyRepository.getCryptoCurrencyBySymbol("BTC"), new BigDecimal("100"));
        cryptosAvailability.put(cryptoCurrencyRepository.getCryptoCurrencyBySymbol("ETH"), new BigDecimal("500"));
        startPriceFluctuation();
    }

    // method to check if there are enough cryptocurrency funds in the exchange
    public void sufficientCryptosInExchangeVal(CryptoCurrency cryptoCurrency, BigDecimal amount) {
        BigDecimal available = cryptosAvailability.get(cryptoCurrency);
        if (available.compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient crypto from exchange");
        }
    }

    // method to check get the total price of a cryptocurrency depending on the amount
    public BigDecimal getTotalPrice(CryptoCurrency cryptoCurrency, BigDecimal amount) {
        BigDecimal price = cryptosMarketPrice.get(cryptoCurrency);
        return price.multiply(amount);
    }

    // method to withdraw the quantity purchased from the exchange
    public void buyCryptoCurrency(CryptoCurrency cryptoCurrency, BigDecimal amount) {
        BigDecimal available = cryptosAvailability.get(cryptoCurrency);
        cryptosAvailability.put(cryptoCurrency, available.subtract(amount));
    }

    // method to get the cryptocurrency by symbol
    public CryptoCurrency getCryptoCurrencyBySymbol(String symbol) {
        return cryptoCurrencyRepository.getCryptoCurrencyBySymbol(symbol);
    }

    // method to start the price fluctuation
    private void startPriceFluctuation() {
        // price fluctuation will be simulated every 20 seconds and start after 30 seconds
        scheduler.scheduleAtFixedRate(this::priceFluctuation, 30, 20, TimeUnit.SECONDS);
    }

    // method to simulate the price fluctuation of the cryptocurrencies
    private void priceFluctuation() {
        for (Map.Entry<CryptoCurrency, BigDecimal> entry : cryptosMarketPrice.entrySet()) {
            BigDecimal price = entry.getValue();
            // fluctuation will be between -2% and 2%
            BigDecimal fluctuation = price.multiply(new BigDecimal("0.02"));
            BigDecimal random = BigDecimal.valueOf(Math.random() * 2 - 1);
            BigDecimal newPrice = price.add(fluctuation.multiply(random));
            // price will be rounded to 2 decimal places
            newPrice = newPrice.setScale(2, RoundingMode.HALF_UP);
            cryptosMarketPrice.put(entry.getKey(), newPrice); // update the price
        }
        observer.priceChanged(); // notify the observer that the price has changed
    }

    public void setObserver(PriceObserver observer) {
        this.observer = observer;
    }

    public String getAvailableCryptosAndMarketPrice() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<CryptoCurrency, BigDecimal> entry : cryptosAvailability.entrySet()) {
            sb.append(entry.getKey().toString());
            sb.append("Price: ");
            sb.append(cryptosMarketPrice.get(entry.getKey()));
            sb.append("  Available: ");
            sb.append(entry.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }
}
