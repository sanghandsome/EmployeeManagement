package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.request.DepartmentRequest;
import com.example.employeemanagement.dto.response.DepartmentResponse;
import com.example.employeemanagement.mapper.DepartmentMapper;
import com.example.employeemanagement.model.Company;
import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.repository.CompanyRepository;
import com.example.employeemanagement.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final CompanyRepository companyRepository;

    public List<DepartmentResponse> getDepartmentByCompanyId(Long companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(()->new RuntimeException("Company does not exist"));
        return departmentRepository.findDepartmentByCompany(company)
                .stream()
                .map(departmentMapper::toDepartmentResponse)
                .toList();
    }

    public DepartmentResponse getDepartmentById(Long id){
        Department department = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department does not exist"));
        return departmentMapper.toDepartmentResponse(department);
    }

    public DepartmentResponse createDepartment(DepartmentRequest departmentRequest){
        Department department = departmentMapper.toDepartment(departmentRequest);
        departmentRepository.save(department);
        return departmentMapper.toDepartmentResponse(department);
    }

    public DepartmentResponse updateDepartment(Long id, DepartmentRequest departmentRequest){
        Department department = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department does not exist"));
        if (!departmentRepository.existsByIdAndCompanyId(id, departmentRequest.getCompany_id())) {
            throw new RuntimeException("Department does not belong to this Company");
        }
        departmentMapper.updateDepartment(departmentRequest,department);
        departmentRepository.save(department);
        return departmentMapper.toDepartmentResponse(department);
    }

    public  void deleteDepartment(Long departmentId, Long companyId){
        if (!departmentRepository.existsByIdAndCompanyId(departmentId, companyId)) {
            throw new RuntimeException("Department does not belong to this Company");
        }
        departmentRepository.deleteById(departmentId);
    }

}
