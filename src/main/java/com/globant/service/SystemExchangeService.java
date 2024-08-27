package com.globant.service;

import com.globant.exceptions.InsufficientFundsException;
import com.globant.models.CryptoCurrency;
import com.globant.repositories.CryptoCurrencyRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class SystemExchangeService {

    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final Map<CryptoCurrency, BigDecimal> cryptosMarketPrice = new HashMap<>();
    private final Map<CryptoCurrency, BigDecimal> cryptosAvailability = new HashMap<>();

    public SystemExchangeService(CryptoCurrencyRepository cryptoCurrencyRepository) {
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
        cryptosMarketPrice.put(cryptoCurrencyRepository.getCryptoCurrencyBySymbol("BTC"), new BigDecimal("50000"));
        cryptosMarketPrice.put(cryptoCurrencyRepository.getCryptoCurrencyBySymbol("ETH"), new BigDecimal("3000"));
        cryptosAvailability.put(cryptoCurrencyRepository.getCryptoCurrencyBySymbol("BTC"), new BigDecimal("100"));
        cryptosAvailability.put(cryptoCurrencyRepository.getCryptoCurrencyBySymbol("ETH"), new BigDecimal("500"));
    }

    public void sufficientCryptosInExchangeVal(String symbol, BigDecimal amount) {
        CryptoCurrency cryptoCurrency = cryptoCurrencyRepository.getCryptoCurrencyBySymbol(symbol);
        BigDecimal available = cryptosAvailability.get(cryptoCurrency);
        if (available.compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient crypto from exchange");
        }
    }

    public BigDecimal getTotalPrice(String symbol, BigDecimal amount) {
        CryptoCurrency cryptoCurrency = cryptoCurrencyRepository.getCryptoCurrencyBySymbol(symbol);
        BigDecimal price = cryptosMarketPrice.get(cryptoCurrency);
        return price.multiply(amount);
    }

    public CryptoCurrency buyCryptoCurrency(String symbol, BigDecimal amount) {
        CryptoCurrency cryptoCurrency = cryptoCurrencyRepository.getCryptoCurrencyBySymbol(symbol);
        BigDecimal available = cryptosAvailability.get(cryptoCurrency);
        cryptosAvailability.put(cryptoCurrency, available.subtract(amount));
        return cryptoCurrency;
    }

    public CryptoCurrency getCryptoCurrencyBySymbol(String symbol) {
        return cryptoCurrencyRepository.getCryptoCurrencyBySymbol(symbol);
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
