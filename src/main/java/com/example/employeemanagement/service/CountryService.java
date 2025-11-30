package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.request.CountryRequest;
import com.example.employeemanagement.dto.response.CountryResponse;
import com.example.employeemanagement.mapper.CountryMapper;
import com.example.employeemanagement.model.Country;
import com.example.employeemanagement.model.PagedResponse;
import com.example.employeemanagement.repository.CountryRepository;

import java.util.List;


public interface CountryService {

    public CountryResponse createCountry(CountryRequest countryRequest);

    public CountryResponse getCountryById (Long id);

    public CountryResponse updateCountry(Long id, CountryRequest countryRequest);

    public void deleteCountry(Long id);

    public PagedResponse<CountryResponse> getAllCountry(int page, int size,String keyword);

}
