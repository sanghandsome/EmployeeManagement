package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.request.PersonRequest;
import com.example.employeemanagement.dto.response.PersonResponse;
import com.example.employeemanagement.model.Person;
import java.util.List;


public interface PersonService {


    public PersonResponse findPersonById(Long id) ;

    public List<PersonResponse> findAllPersons(int page, int size) ;

    public List<PersonResponse> findAllPersonsByCompany(Long companyId, int page, int size) ;

    public PersonResponse createPerson(PersonRequest personRequest) ;

    public PersonResponse updatePerson(PersonRequest personRequest, Long id) ;

    public  void deletePerson(Long id) ;

}
