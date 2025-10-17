package com.example.employeemanagement.mapper;

import com.example.employeemanagement.dto.request.CountryRequest;
import com.example.employeemanagement.dto.response.CountryResponse;
import com.example.employeemanagement.model.Country;

public class CountryMapper {

    public static CountryResponse toCountryResponse(Country country) {
        if (country == null) {
            return null;
        }
        return CountryResponse.builder()
                .id(country.getId())
                .name(country.getName())
                .description(country.getDescription())
                .code(country.getCode())
                .build();
    }

    public static Country toCountry(CountryRequest countryRequest) {
        if (countryRequest == null) {
            return null;
        }
        Country country = new Country();
        country.setName(countryRequest.getName());
        country.setDescription(countryRequest.getDescription());
        country.setCode(countryRequest.getCode());
        return country;
    }

    public static Country updateCountry(Country country, CountryRequest countryRequest) {
        if (country == null) {
            return null;
        }
        country.setName(countryRequest.getName());
        country.setCode(countryRequest.getCode());
        country.setDescription(countryRequest.getDescription());
        return country;
    }
}
