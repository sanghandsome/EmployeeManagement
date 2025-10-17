package com.example.employeemanagement.mapper;

import com.example.employeemanagement.dto.request.CompanyRequest;
import com.example.employeemanagement.dto.response.CompanyResponse;
import com.example.employeemanagement.model.Company;

public class CompanyMapper {
    public static CompanyResponse toCompanyResponse(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .code(company.getCode())
                .address(company.getAddress())
                .build();
    }

    public static Company toCompany(CompanyRequest companyRequest) {
        Company company = new Company();
        company.setName(companyRequest.getName());
        company.setCode(companyRequest.getCode());
        company.setAddress(companyRequest.getAddress());
        return company;
    }

    public static Company updateCompany(CompanyRequest companyRequest, Company company) {
        company.setName(companyRequest.getName());
        company.setCode(companyRequest.getCode());
        company.setAddress(companyRequest.getAddress());
        return company;
    }
}
