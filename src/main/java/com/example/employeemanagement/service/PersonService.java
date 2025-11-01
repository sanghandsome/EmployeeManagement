package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.request.PersonRequest;
import com.example.employeemanagement.dto.response.PersonResponse;
import com.example.employeemanagement.model.Person;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface PersonService {


    public PersonResponse findPersonById(Long id) ;

    public List<PersonResponse> findAllPersons(int page, int size) ;

    public List<PersonResponse> findAllPersonsByCompany(Long companyId, int page, int size) ;

    public String uploadAvatar(MultipartFile avatarFile);

    public String updateAvatar(Long id, MultipartFile avatarFile);

    public PersonResponse createPerson(PersonRequest personRequest,MultipartFile avatarFile) ;

    public PersonResponse updatePerson(PersonRequest personRequest, Long id,MultipartFile avatarFile) ;

    public  void deletePerson(Long id) ;

}
