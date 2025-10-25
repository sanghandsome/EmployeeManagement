package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.dto.request.PersonRequest;
import com.example.employeemanagement.dto.response.PersonResponse;
import com.example.employeemanagement.mapper.PersonMapper;
import com.example.employeemanagement.model.Person;
import com.example.employeemanagement.repository.PersonRepository;
import com.example.employeemanagement.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    public PersonResponse findPersonById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person does not exist"));
        return personMapper.toPersonResponse(person);
    }

    @Override
    public List<PersonResponse> findAllPersons(int page, int size) {
        return personRepository.findAllPersonWithPagination(page, size)
                .stream()
                .map(personMapper::toPersonResponse)
                .toList();
    }

    @Override
    public List<PersonResponse> findAllPersonsByCompany(Long companyId, int page, int size) {

        return personRepository.findByCompanyIdWithPagination(companyId, page, size)
                .stream()
                .map(personMapper::toPersonResponse)
                .toList();
    }

    @Override
    public PersonResponse createPerson(PersonRequest personRequest) {
        Person personCreate = personMapper.toPersonFromPersonRequest(personRequest);
        personRepository.save(personCreate);
        return personMapper.toPersonResponse(personCreate);
    }

    @Override
    public PersonResponse updatePerson(PersonRequest personRequest, Long id) {
        Person personUpdate = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person does not exist"));
        personMapper.updatePerson(personUpdate, personRequest);
        personRepository.save(personUpdate);
        return personMapper.toPersonResponse(personUpdate);
    }

    @Override
    public  void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

}
