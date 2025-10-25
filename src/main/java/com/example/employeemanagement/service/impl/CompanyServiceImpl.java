package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.dto.request.CompanyRequest;
import com.example.employeemanagement.dto.response.CompanyResponse;
import com.example.employeemanagement.mapper.CompanyMapper;
import com.example.employeemanagement.model.Company;
import com.example.employeemanagement.repository.CompanyRepository;
import com.example.employeemanagement.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    @Override
    public List<CompanyResponse> findAll(int page, int size) {
        return companyRepository.findAllCompanyWithPagination(page, size)
                .stream()
                .map(CompanyMapper::toCompanyResponse)
                .toList();
    }

    @Override
    public CompanyResponse findById(Long id) {
        return CompanyMapper.toCompanyResponse(companyRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Company does not exist")));
    }

    @Override
    public CompanyResponse createCompany(CompanyRequest companyRequest) {
        Company company = CompanyMapper.toCompany(companyRequest);
        companyRepository.save(company);
        return CompanyMapper.toCompanyResponse(company);
    }

    @Override
    public CompanyResponse updateCompany(CompanyRequest companyRequest, Long id) {
        Company companyUpdate = companyRepository.findById(id).orElseThrow(()->new RuntimeException("Company does not exist"));
        CompanyMapper.updateCompany(companyRequest, companyUpdate);
        companyRepository.save(companyUpdate);
        return CompanyMapper.toCompanyResponse(companyUpdate);
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}
