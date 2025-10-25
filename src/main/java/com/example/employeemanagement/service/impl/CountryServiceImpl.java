package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.dto.request.CountryRequest;
import com.example.employeemanagement.dto.response.CountryResponse;
import com.example.employeemanagement.mapper.CountryMapper;
import com.example.employeemanagement.model.Country;
import com.example.employeemanagement.repository.CountryRepository;
import com.example.employeemanagement.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    public CountryResponse createCountry(CountryRequest countryRequest) {
        Country countryCreate = CountryMapper.toCountry(countryRequest);
        countryRepository.save(countryCreate);
        return CountryMapper.toCountryResponse(countryCreate);
    }

    @Override
    public CountryResponse getCountryById (Long id) {
        Country country = countryRepository.findById(id).orElseThrow(() -> new RuntimeException("Country does not exist"));
        return CountryMapper.toCountryResponse(country);
    }

    @Override
    public CountryResponse updateCountry(Long id, CountryRequest countryRequest) {
        Country updatedCountry = countryRepository.findById(id).orElseThrow(() -> new RuntimeException("Country does not exist"));
        CountryMapper.updateCountry(updatedCountry,countryRequest);
        countryRepository.save(updatedCountry);
        return CountryMapper.toCountryResponse(updatedCountry);
    }

    @Override
    public void deleteCountry(Long id) {
        if(!countryRepository.existsById(id)) {
            throw new RuntimeException("Country does not exist");
        }
        countryRepository.deleteById(id);
    }
}
