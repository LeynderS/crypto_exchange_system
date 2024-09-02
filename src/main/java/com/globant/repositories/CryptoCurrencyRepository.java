package com.globant.repositories;

import com.globant.exceptions.UnknownCryptoCurrencyException;
import com.globant.models.CryptoCurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Repository class that stores the available cryptocurrencies.
 */
public class CryptoCurrencyRepository {
    // Map that stores the cryptocurrencies by symbol.
    private final Map<String, CryptoCurrency> cryptoCurrencies = new HashMap<>();

    public CryptoCurrencyRepository() {
        cryptoCurrencies.put("BTC", new CryptoCurrency("BTC", "Bitcoin"));
        cryptoCurrencies.put("ETH", new CryptoCurrency("ETH", "Ethereum"));
    }

    /**
     * Retrieves a cryptocurrency by symbol.
     *
     * @param symbol the symbol to search for
     * @return the cryptocurrency with the given symbol
     * @throws UnknownCryptoCurrencyException if the cryptocurrency is not found
     */
    public CryptoCurrency getCryptoCurrencyBySymbol(String symbol) {
        return Optional.ofNullable(cryptoCurrencies.get(symbol)).
                orElseThrow(UnknownCryptoCurrencyException::new);
    }

}
