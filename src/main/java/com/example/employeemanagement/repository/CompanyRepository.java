package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Company;
import com.example.employeemanagement.repository.custom.CompanyCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>, CompanyCustomRepository {
}
