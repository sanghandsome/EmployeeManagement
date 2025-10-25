package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.ProjectPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectPersonRepository extends JpaRepository<ProjectPerson, Long> {
}
