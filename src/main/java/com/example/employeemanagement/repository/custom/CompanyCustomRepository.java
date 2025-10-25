package com.example.employeemanagement.repository.custom;

import com.example.employeemanagement.model.Company;

import java.util.List;

public interface CompanyCustomRepository {
    List<Company> findAllCompanyWithPagination( int page, int size);
}
