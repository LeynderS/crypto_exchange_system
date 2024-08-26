package com.globant.repositories;

import com.globant.exceptions.UnknowCryptoCurrencyException;
import com.globant.models.CryptoCurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CryptoCurrencyRepository {
    private final Map<String, CryptoCurrency> cryptoCurrencies = new HashMap<>();

    public CryptoCurrencyRepository() {
        cryptoCurrencies.put("BTC", new CryptoCurrency("BTC", "Bitcoin"));
        cryptoCurrencies.put("ETH", new CryptoCurrency("ETH", "Ethereum"));
    }
    public CryptoCurrency getCryptoCurrencyBySymbol(String symbol) {
        return Optional.ofNullable(cryptoCurrencies.get(symbol)).
                orElseThrow(UnknowCryptoCurrencyException::new);
    }

}
