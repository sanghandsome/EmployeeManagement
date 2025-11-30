package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.dto.request.DepartmentRequest;
import com.example.employeemanagement.dto.response.DepartmentResponse;
import com.example.employeemanagement.mapper.DepartmentMapper;
import com.example.employeemanagement.model.Company;
import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.repository.CompanyRepository;
import com.example.employeemanagement.repository.DepartmentRepository;
import com.example.employeemanagement.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final CompanyRepository companyRepository;

    @Override
    public List<DepartmentResponse> getDepartmentByCompanyId(Long companyId,Long departmentParentId, int page, int size) {
        Department departmentParent = departmentRepository.findById(departmentParentId).orElseThrow(() -> new RuntimeException("Department parent does not exist"));
        if (!departmentRepository.existsByIdAndCompanyId(companyId, departmentParent.getCompany().getId())) {
            throw new RuntimeException("Department does not belong to this Company");
        }
        return departmentRepository.findDepartmentByCompanyWithPagination(companyId,departmentParentId,page,size)
                .stream()
                .map(departmentMapper::toDepartmentResponse)
                .toList();
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id){
        Department department = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department does not exist"));
        return departmentMapper.toDepartmentResponse(department);
    }

    @Override
    public DepartmentResponse createDepartment(DepartmentRequest departmentRequest){
        Department department = departmentMapper.toDepartment(departmentRequest);
        Department departmentParent = departmentRepository.findById(departmentRequest.getParent_id()).orElseThrow(() -> new RuntimeException("Department parent does not exist"));
        if (!departmentParent.getCompany().getId().equals(departmentRequest.getCompany_id())) {
            throw new RuntimeException("The parent department is not part of this company.");
        }
        departmentRepository.save(department);
        return departmentMapper.toDepartmentResponse(department);
    }

    @Override
    public DepartmentResponse updateDepartment(Long id, DepartmentRequest departmentRequest){
        Department departmentParent = departmentRepository.findById(departmentRequest.getParent_id()).orElseThrow(() -> new RuntimeException("Department parent does not exist"));
        Department department = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department does not exist"));
        if (!departmentRepository.existsByIdAndCompanyId(id, departmentRequest.getCompany_id())) {
            throw new RuntimeException("Department does not belong to this Company");
        }
        if (!departmentParent.getCompany().getId().equals(departmentRequest.getCompany_id())) {
            throw new RuntimeException("The parent department is not part of this company.");
        }
        departmentMapper.updateDepartment(departmentRequest,department);
        departmentRepository.save(department);
        return departmentMapper.toDepartmentResponse(department);
    }

    @Override
    public  void deleteDepartment(Long departmentId, Long companyId){
        if (!departmentRepository.existsByIdAndCompanyId(departmentId, companyId)) {
            throw new RuntimeException("Department does not belong to this Company");
        }
        departmentRepository.deleteById(departmentId);
    }

}
