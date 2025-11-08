package com.example.employeemanagement.repository.custom;

import com.example.employeemanagement.model.Country;

import java.util.List;

public interface CountryCustomRepository {
    List<Country> findAllCountryWithPagination( int page, int size);
}
