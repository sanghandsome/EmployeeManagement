package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.request.PersonRequest;
import com.example.employeemanagement.dto.response.PersonResponse;
import com.example.employeemanagement.mapper.PersonMapper;
import com.example.employeemanagement.model.Person;
import com.example.employeemanagement.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public PersonResponse findPersonById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person does not exist"));
        return personMapper.toPersonResponse(person);
    }

    public List<PersonResponse> findAllPersons() {
        return personRepository.findAll()
                .stream()
                .map(personMapper::toPersonResponse)
                .toList();
    }

    public List<PersonResponse> findAllPersonsByCompany(Long companyId) {

        return personRepository.findByCompany_Id(companyId)
                .stream()
                .map(personMapper::toPersonResponse)
                .toList();
    }


    public PersonResponse createPerson(PersonRequest personRequest) {
        Person personCreate = personMapper.toPersonFromPersonRequest(personRequest);
        personRepository.save(personCreate);
        return personMapper.toPersonResponse(personCreate);
    }

    public PersonResponse updatePerson(PersonRequest personRequest, Long id) {
        Person personUpdate = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person does not exist"));
        personMapper.updatePerson(personUpdate, personRequest);
        personRepository.save(personUpdate);
        return personMapper.toPersonResponse(personUpdate);
    }

    public  void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

}
