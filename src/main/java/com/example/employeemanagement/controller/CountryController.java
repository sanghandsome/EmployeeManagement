package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.request.CountryRequest;
import com.example.employeemanagement.dto.response.ApiResponse;
import com.example.employeemanagement.dto.response.CountryResponse;
import com.example.employeemanagement.model.PagedResponse;
import com.example.employeemanagement.service.CountryService;
import com.example.employeemanagement.service.impl.CountryServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/countries")
@RequiredArgsConstructor
@Validated
public class CountryController {
    private final CountryServiceImpl countryService;

    @GetMapping
    public ResponseEntity<PagedResponse<CountryResponse>> getAllCountries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword
    ) {
        return ResponseEntity.ok(countryService.getAllCountry(page, size, keyword));
    }

    @GetMapping("/{id}")
    public ApiResponse<CountryResponse> getCountryById(@PathVariable @Positive(message = "Id must be greater than 0") Long id) {
        CountryResponse countryResponse = countryService.getCountryById(id);
        return ApiResponse.<CountryResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Country retrieved successfully")
                .data(countryResponse)
                .build();
    }

    @PostMapping
    public ApiResponse<CountryResponse> createCountry(@Valid @RequestBody CountryRequest countryRequest) {
        CountryResponse responseCreate  = countryService.createCountry(countryRequest);
        return ApiResponse.<CountryResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Country created successfully")
                .data(responseCreate)
                .build();
    }


    @PatchMapping("/{id}")
    public ApiResponse<CountryResponse> update(@PathVariable @Positive(message = "Id must be greater than 0") Long id, @Valid @RequestBody CountryRequest countryRequest) {
        CountryResponse responseUpdate = countryService.updateCountry(id, countryRequest);
        return ApiResponse.<CountryResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Country updated successfully")
                .data(responseUpdate)
                .build();
    }

    @DeleteMapping("{id}")
    public ApiResponse<Void> delete(@PathVariable @Positive(message = "Id must be greater than 0") Long id) {
        countryService.deleteCountry(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Country deleted successfully")
                .data(null)
                .build();

    }

}
