package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.request.DepartmentRequest;
import com.example.employeemanagement.dto.response.DepartmentResponse;
import java.util.List;


public interface DepartmentService {

    public List<DepartmentResponse> getDepartmentByCompanyId(Long companyId,Long departmentParentId, int page, int size);

    public DepartmentResponse getDepartmentById(Long id);

    public DepartmentResponse createDepartment(DepartmentRequest departmentRequest);

    public DepartmentResponse updateDepartment(Long id, DepartmentRequest departmentRequest);

    public  void deleteDepartment(Long departmentId, Long companyId);

}
