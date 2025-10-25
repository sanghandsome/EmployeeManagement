package com.example.employeemanagement.service;


import com.example.employeemanagement.dto.request.CompanyRequest;
import com.example.employeemanagement.dto.response.CompanyResponse;
import com.example.employeemanagement.mapper.CompanyMapper;
import com.example.employeemanagement.model.Company;
import com.example.employeemanagement.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CompanyService {

    public List<CompanyResponse> findAll(int page, int size);

    public CompanyResponse findById(Long id);

    public CompanyResponse createCompany(CompanyRequest companyRequest);

    public CompanyResponse updateCompany(CompanyRequest companyRequest, Long id);

    public void deleteCompany(Long id);
}
