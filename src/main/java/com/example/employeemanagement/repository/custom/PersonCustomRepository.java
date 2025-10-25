package com.example.employeemanagement.repository.custom;

import com.example.employeemanagement.model.Person;

import java.util.List;

public interface PersonCustomRepository {
    List<Person> findByCompanyIdWithPagination(Long companyId, int page, int size);
    List<Person> findAllPersonWithPagination( int page, int size);
}
