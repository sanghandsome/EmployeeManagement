package com.example.employeemanagement.repository;

import com.example.employeemanagement.dto.response.DepartmentResponse;
import com.example.employeemanagement.model.Company;
import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.repository.custom.DepartmentCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long>, DepartmentCustomRepository { }
