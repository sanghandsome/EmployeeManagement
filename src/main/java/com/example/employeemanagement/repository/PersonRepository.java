package com.example.employeemanagement.repository;

import com.example.employeemanagement.dto.response.PersonResponse;
import com.example.employeemanagement.model.Person;
import com.example.employeemanagement.repository.custom.PersonCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> , PersonCustomRepository {}
