package com.example.employeemanagement.controller;


import com.example.employeemanagement.dto.request.CompanyRequest;
import com.example.employeemanagement.dto.request.DepartmentRequest;
import com.example.employeemanagement.dto.response.CompanyResponse;
import com.example.employeemanagement.dto.response.DepartmentResponse;
import com.example.employeemanagement.service.CompanyService;
import com.example.employeemanagement.service.DepartmentService;
import com.example.employeemanagement.service.impl.CompanyServiceImpl;
import com.example.employeemanagement.service.impl.DepartmentServiceImpl;
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
    private final CompanyServiceImpl companyService;
    private final DepartmentServiceImpl departmentService;

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> findAllCompanies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(companyService.findAll(page, size));
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

    @GetMapping("/{companyId}/departments")
    public ResponseEntity<List<DepartmentResponse>> findDepartmentByCompany(
            @PathVariable @Positive(message = "Id must be greater than 0") Long companyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(departmentService.getDepartmentByCompanyId(companyId, page, size));
    }

    @PostMapping("/{companyId}/departments")
    public ResponseEntity<DepartmentResponse> createDepartment(@Valid @RequestBody DepartmentRequest departmentRequest, @PathVariable @Positive(message = "Id must be greater than 0") Long companyId) {
        departmentRequest.setCompany_id(companyId);
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.createDepartment(departmentRequest));
    }

    @PatchMapping("/{companyId}/departments/{departmentId}")
    public  ResponseEntity<DepartmentResponse> updateDepartment(@Valid @RequestBody DepartmentRequest departmentRequest, @PathVariable @Positive(message = "Id must be greater than 0") Long companyId, @PathVariable @Positive(message = "Id must be greater than 0") Long departmentId) {
        departmentRequest.setCompany_id(companyId);
        return ResponseEntity.ok(departmentService.updateDepartment(departmentId, departmentRequest));
    }

    @DeleteMapping("/{companyId}/departments/{departmentId}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable @Positive(message = "Id must be greater than 0") Long departmentId, @PathVariable @Positive(message = "Id must be greater than 0") Long companyId) {
        departmentService.deleteDepartment(departmentId,companyId);
        return ResponseEntity.ok().build();
    }


}
