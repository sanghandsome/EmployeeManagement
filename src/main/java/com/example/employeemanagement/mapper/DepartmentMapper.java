package com.example.employeemanagement.mapper;

import com.example.employeemanagement.dto.request.DepartmentRequest;
import com.example.employeemanagement.dto.response.DepartmentResponse;
import com.example.employeemanagement.model.Company;
import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.repository.CompanyRepository;
import com.example.employeemanagement.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepartmentMapper {

    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;

    public DepartmentResponse toDepartmentResponse(Department department) {
        if (department == null) {
            return null;
        }
        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .code(department.getCode())
                .parent_name(department.getParent() != null ? department.getParent().getName() : null)
                .company_name(department.getCompany().getName())
                .build();
    }

    public Department toDepartment(DepartmentRequest departmentRequest) {
        if (departmentRequest == null) {
            return null;
        }

        Department parent = null;
        if (departmentRequest.getParent_id() != null) {
            parent = departmentRepository
                    .findById(departmentRequest.getParent_id())
                    .orElseThrow(() -> new RuntimeException("Parent department does not exist"));
        }

        Company company = companyRepository
                .findById(departmentRequest.getCompany_id())
                .orElseThrow(() -> new RuntimeException("Company does not exist"));

        Department department = new Department();
        department.setName(departmentRequest.getName());
        department.setCode(departmentRequest.getCode());
        department.setParent(parent);
        department.setCompany(company);

        return department;
    }

    public void updateDepartment(DepartmentRequest departmentRequest, Department department) {
        if (departmentRequest == null) {
            return;
        }
        Department parent = null;
        if (departmentRequest.getParent_id() != null) {
            parent = departmentRepository
                    .findById(departmentRequest.getParent_id())
                    .orElseThrow(() -> new RuntimeException("Parent department does not exist"));
        }

        Company company = companyRepository
                .findById(departmentRequest.getCompany_id())
                .orElseThrow(() -> new RuntimeException("Company does not exist"));

        department.setName(departmentRequest.getName());
        department.setCode(departmentRequest.getCode());
        department.setParent(parent);
        department.setCompany(company);
    }

}
