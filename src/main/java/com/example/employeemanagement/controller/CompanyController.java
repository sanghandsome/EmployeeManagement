package com.example.employeemanagement.controller;


import com.example.employeemanagement.dto.request.CompanyRequest;
import com.example.employeemanagement.dto.response.CompanyResponse;
import com.example.employeemanagement.service.CompanyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> findAllCompanies() {
        return ResponseEntity.ok(companyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> findCompanyById(@PathVariable @Positive(message = "Id must be greater than 0") Long id) {
        return ResponseEntity.ok(companyService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(@Valid @RequestBody CompanyRequest companyRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(companyRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CompanyResponse> updateCompany(@Valid @RequestBody CompanyRequest companyRequest,@PathVariable @Positive(message = "Id must be greater than 0") Long id) {
        return ResponseEntity.ok(companyService.updateCompany(companyRequest, id));
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable @Positive(message = "Id must be greater than 0") Long id) {
        companyService.deleteCompany(id);
    }
}
