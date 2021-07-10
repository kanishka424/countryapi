package com.backend.countryapi.controller;

import com.backend.countryapi.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/country")
public class CountryResource {

    private final CountryService countryService;

    @Autowired
    public CountryResource(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public List<String> getAllCountries() {
        return countryService.getCurrencySymbolsMoreThanTwo();
    }

    @GetMapping("/region/{regionCode}")
    public List<String> regionCountries(@PathVariable String regionCode) {
        return countryService.getAllCountriesByRegion(regionCode);
    }

    @GetMapping("/{code1}/{code2}")
    public Double timezoneDiff(@PathVariable String code1, @PathVariable String code2) {
        return countryService.timeZoneDiff(code1, code2);
    }


}
