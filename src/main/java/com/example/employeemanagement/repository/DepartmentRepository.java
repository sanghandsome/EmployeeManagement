package com.example.employeemanagement.repository;

import com.example.employeemanagement.dto.response.DepartmentResponse;
import com.example.employeemanagement.model.Company;
import com.example.employeemanagement.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
    List<Department> findDepartmentByCompany(Company company);
    boolean existsByIdAndCompanyId(Long departmentId, Long companyId);
}
