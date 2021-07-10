package com.backend.countryapi.controller;

import com.backend.countryapi.model.Country;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(url = "https://restcountries.eu", name = "restcountries")
public interface CountryClient {

    @GetMapping("/rest/v2/all")
    List<Country> getAllCountries();
}
