package com.example.employeemanagement.repository.custom;

import com.example.employeemanagement.model.Department;

import java.util.List;

public interface DepartmentCustomRepository {
    List<Department> findDepartmentByCompanyWithPagination(Long company_id, int page, int size);
    Boolean existsByIdAndCompanyId(Long departmentId, Long companyId);
}
