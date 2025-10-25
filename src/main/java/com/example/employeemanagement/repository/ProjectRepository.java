package com.example.employeemanagement.repository;

import com.example.employeemanagement.dto.request.ProjectRequest;
import com.example.employeemanagement.model.Company;
import com.example.employeemanagement.model.Person;
import com.example.employeemanagement.model.Project;
import com.example.employeemanagement.repository.custom.ProjectCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectCustomRepository {}
