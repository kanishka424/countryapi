package com.backend.countryapi.service;

import com.backend.countryapi.model.Country;
import com.backend.countryapi.controller.CountryClient;
import com.backend.countryapi.model.Currency;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryService {


    private final CountryClient countryClient;

    public CountryService(CountryClient countryClient) {
        this.countryClient = countryClient;
    }


    public List<String> getCurrencySymbolsMoreThanTwo() {
        List<Country> countries = this.countryClient.getAllCountries();

        HashMap<String, Integer> currencyMap = new HashMap<>();

        countries.forEach(country -> {
            List<Currency> currencies = country.getCurrencies();
            currencies.forEach(currency -> {

                if (currencyMap.get(currency.getSymbol()) == null) {
                    currencyMap.put(currency.getSymbol(), 1);
                } else {
                    currencyMap.put(currency.getSymbol(), currencyMap.get(currency.getSymbol()) + 1);
                }

            });
        });


        currencyMap.remove(null);

        return currencyMap.entrySet().stream().filter(stringIntegerEntry -> stringIntegerEntry.getValue() > 1)
                .map(stringIntegerEntry -> stringIntegerEntry.getKey()).collect(Collectors.toList());
    }

    public Double timeZoneDiff(String countryCode1, String countryCode2) {
        Optional<Country> optionalCountry1 = this.countryClient.getAllCountries().stream()
                .filter(country -> country.getAlpha2Code().equals(countryCode1)
                        || country.getAlpha3Code().equals(countryCode1)).findFirst();

        Optional<Country> optionalCountry2 = this.countryClient.getAllCountries().stream()
                .filter(country -> country.getAlpha2Code().equals(countryCode2)
                        || country.getAlpha3Code().equals(countryCode2)).findFirst();

        if (optionalCountry1.isPresent() && optionalCountry2.isPresent()) {
            Country country1 = optionalCountry1.get();
            Country country2 = optionalCountry2.get();

            LocalDateTime now = LocalDateTime.now();
            ZoneOffset offset1 = now.atZone(ZoneId.of(country1.getTimezones()[0])).getOffset();
            ZoneOffset offset2 = now.atZone(ZoneId.of(country2.getTimezones()[0])).getOffset();

            return Math.abs((double) offset1.compareTo(offset2) / (60 * 60));
        } else {
            throw new RuntimeException(("Country not found"));
        }
    }

    public List<String> getAllCountriesByRegion(String regionCode) {

        List<Country> countries = this.countryClient.getAllCountries();

        return countries.stream().filter(country -> country.getRegion().equals(regionCode)).
                sorted((o1, o2) -> o1.getPopulation() > o2.getPopulation() ? 0: 1)
                .map(country -> country.getName() +" "+country.getPopulation() ).collect(Collectors.toList());
    }

}
