package com.example.employeemanagement.repository;

import com.example.employeemanagement.dto.response.PersonResponse;
import com.example.employeemanagement.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByCompany_Id(Long companyId);
}
