package com.example.employeemanagement.service;


import com.example.employeemanagement.dto.request.CompanyRequest;
import com.example.employeemanagement.dto.response.CompanyResponse;
import com.example.employeemanagement.mapper.CompanyMapper;
import com.example.employeemanagement.model.Company;
import com.example.employeemanagement.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public List<CompanyResponse> findAll() {
        return companyRepository.findAll()
                .stream()
                .map(CompanyMapper::toCompanyResponse)
                .toList();
    }

    public CompanyResponse findById(Long id) {
        return CompanyMapper.toCompanyResponse(companyRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Company does not exist")));
    }

    public CompanyResponse createCompany(CompanyRequest companyRequest) {
        Company company = CompanyMapper.toCompany(companyRequest);
        companyRepository.save(company);
        return CompanyMapper.toCompanyResponse(company);
    }

    public CompanyResponse updateCompany(CompanyRequest companyRequest, Long id) {
        Company companyUpdate = companyRepository.findById(id).orElseThrow(()->new RuntimeException("Company does not exist"));
        CompanyMapper.updateCompany(companyRequest, companyUpdate);
        companyRepository.save(companyUpdate);
        return CompanyMapper.toCompanyResponse(companyUpdate);
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}
