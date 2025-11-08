package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Country;
import com.example.employeemanagement.repository.custom.CountryCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long>, CountryCustomRepository {
    @Query("SELECT COUNT(c) FROM Country c")
    Long countAllCountries();
}
