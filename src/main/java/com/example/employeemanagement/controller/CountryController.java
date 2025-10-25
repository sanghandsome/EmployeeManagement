package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.request.CountryRequest;
import com.example.employeemanagement.dto.response.CountryResponse;
import com.example.employeemanagement.service.CountryService;
import com.example.employeemanagement.service.impl.CountryServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("countries")
@RequiredArgsConstructor
@Validated
public class CountryController {
    private final CountryServiceImpl countryService;

    @GetMapping("/{id}")
    public ResponseEntity<CountryResponse> getCountryById(@PathVariable @Positive(message = "Id must be greater than 0") Long id) {
        CountryResponse countryResponse = countryService.getCountryById(id);
        return ResponseEntity.ok(countryResponse);
    }

    @PostMapping
    public ResponseEntity<CountryResponse> createCountry(@Valid @RequestBody CountryRequest countryRequest) {
        CountryResponse responseCreate  = countryService.createCountry(countryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseCreate);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<CountryResponse> update(@PathVariable @Positive(message = "Id must be greater than 0") Long id, @Valid @RequestBody CountryRequest countryRequest) {
        try {
            CountryResponse responseUpdate = countryService.updateCountry(id, countryRequest);
            return ResponseEntity.ok(responseUpdate);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive(message = "Id must be greater than 0") Long id) {
        try {
            countryService.deleteCountry(id);
            return  ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
