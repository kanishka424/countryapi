package com.backend.countryapi.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Country {

    String name;
    String alpha2Code;
    String alpha3Code;
    String[] timezones;
    String region;
    Long population;
    @Builder.Default
    List<Currency> currencies = new ArrayList<>();
}
