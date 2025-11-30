package com.example.employeemanagement.controller;


import com.example.employeemanagement.dto.request.CompanyRequest;
import com.example.employeemanagement.dto.request.DepartmentRequest;
import com.example.employeemanagement.dto.response.ApiResponse;
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
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyServiceImpl companyService;
    private final DepartmentServiceImpl departmentService;

    @GetMapping
    public ApiResponse<List<CompanyResponse>> findAllCompanies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.<List<CompanyResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Companies retrieved successfully")
                .data(companyService.findAll(page, size))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CompanyResponse> findCompanyById(@PathVariable @Positive(message = "Id must be greater than 0") Long id) {
        return ApiResponse.<CompanyResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Company retrieved successfully")
                .data(companyService.findById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<CompanyResponse> createCompany(@Valid @RequestBody CompanyRequest companyRequest) {
        return ApiResponse.<CompanyResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Company created successfully")
                .data(companyService.createCompany(companyRequest))
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<CompanyResponse> updateCompany(@Valid @RequestBody CompanyRequest companyRequest,@PathVariable @Positive(message = "Id must be greater than 0") Long id) {
        return ApiResponse.<CompanyResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Company updated successfully")
                .data(companyService.updateCompany(companyRequest, id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCompany(@PathVariable @Positive(message = "Id must be greater than 0") Long id) {
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Company deleted successfully")
                .data(null)
                .build();
    }

    @GetMapping("/{companyId}/departments")
    public ApiResponse<List<DepartmentResponse>> findDepartmentByCompany(
            @PathVariable @Positive(message = "Id must be greater than 0") Long companyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long departmentParentId
    ) {
        return ApiResponse.<List<DepartmentResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Departments retrieved successfully")
                .data(departmentService.getDepartmentByCompanyId(companyId,departmentParentId, page, size))
                .build();
    }


    @PostMapping("/{companyId}/departments")
    public ApiResponse<DepartmentResponse> createDepartment(@Valid @RequestBody DepartmentRequest departmentRequest, @PathVariable @Positive(message = "Id must be greater than 0") Long companyId) {
        departmentRequest.setCompany_id(companyId);
        return ApiResponse.<DepartmentResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Department created successfully")
                .data(departmentService.createDepartment(departmentRequest))
                .build();
    }

    @PatchMapping("/{companyId}/departments/{departmentId}")
    public  ApiResponse<DepartmentResponse> updateDepartment(@Valid @RequestBody DepartmentRequest departmentRequest, @PathVariable @Positive(message = "Id must be greater than 0") Long companyId, @PathVariable @Positive(message = "Id must be greater than 0") Long departmentId) {
        departmentRequest.setCompany_id(companyId);
        return ApiResponse.<DepartmentResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Department updated successfully")
                .data(departmentService.updateDepartment(departmentId, departmentRequest))
                .build();
    }

    @DeleteMapping("/{companyId}/departments/{departmentId}")
    public ApiResponse<Void> deleteDepartment(@PathVariable @Positive(message = "Id must be greater than 0") Long departmentId, @PathVariable @Positive(message = "Id must be greater than 0") Long companyId) {
        departmentService.deleteDepartment(departmentId,companyId);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Department deleted successfully")
                .data(null)
                .build();
    }


}
