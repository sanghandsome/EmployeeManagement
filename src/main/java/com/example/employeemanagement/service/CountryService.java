package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.request.CountryRequest;
import com.example.employeemanagement.dto.response.CountryResponse;
import com.example.employeemanagement.model.Country;
import com.example.employeemanagement.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;

    public Long createCountry(CountryRequest countryRequest) {
        Country country = new Country();
        country.setName(countryRequest.getName());
        country.setCode(countryRequest.getCode());
        country.setDescription(countryRequest.getDescription());
        Country savedCountry = countryRepository.save(country);
        return savedCountry.getId();
    }

    public CountryResponse getCountryById (Long id) {
        Country country = countryRepository.findById(id).orElseThrow(() -> new RuntimeException("Country does not exist"));
        return CountryResponse.builder()
                .id(country.getId())
                .name(country.getName())
                .code(country.getCode())
                .description(country.getDescription())
                .build();
    }

    public Long updateCountry(Long id, CountryRequest countryRequest) {
        Country updatedCountry = countryRepository.findById(id).orElseThrow(() -> new RuntimeException("Country does not exist"));
        updatedCountry.setName(countryRequest.getName());
        updatedCountry.setCode(countryRequest.getCode());
        updatedCountry.setDescription(countryRequest.getDescription());
        countryRepository.save(updatedCountry);
        return updatedCountry.getId();
    }

    public void deleteCountry(Long id) {
        if(!countryRepository.existsById(id)) {
            throw new RuntimeException("Country does not exist");
        }
        countryRepository.deleteById(id);
    }

}
