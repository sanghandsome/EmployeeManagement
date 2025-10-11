package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.request.PersonRequest;
import com.example.employeemanagement.dto.response.PersonResponse;
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

    public PersonResponse findPersonById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person does not exist"));
        return PersonResponse.builder()
                .id(person.getId())
                .full_name(person.getFull_name())
                .gender(person.getGender())
                .birthDate(person.getBirthdate())
                .address(person.getAddress())
                .build();
    }

    public List<PersonResponse> findAllPersons() {
        List<Person> persons = personRepository.findAll();
        List<PersonResponse> personResponseList = new ArrayList<>();
        for (Person person : persons) {
            PersonResponse personResponse = PersonResponse.builder()
                    .id(person.getId())
                    .full_name(person.getFull_name())
                    .gender(person.getGender())
                    .birthDate(person.getBirthdate())
                    .address(person.getAddress())
                    .build();
            personResponseList.add(personResponse);
        }
        return personResponseList;
    }

    public PersonResponse createPerson(PersonRequest personRequest) {
        Person personCreate = new Person();
        personCreate.setFull_name(personRequest.getFull_name());
        personCreate.setGender(personRequest.getGender());
        personCreate.setPhone_number(personRequest.getPhone_number());
        personCreate.setAddress(personRequest.getAddress());
        personCreate.setBirthdate(personRequest.getBirthDate());
        personRepository.save(personCreate);
        return PersonResponse.builder()
                .id(personCreate.getId())
                .full_name(personCreate.getFull_name())
                .gender(personCreate.getGender())
                .birthDate(personCreate.getBirthdate())
                .address(personCreate.getAddress())
                .build();
    }

    public PersonResponse updatePerson(PersonRequest personRequest, Long id) {
        Person personUpdate = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person does not exist"));
        personUpdate.setFull_name(personRequest.getFull_name());
        personUpdate.setGender(personRequest.getGender());
        personUpdate.setPhone_number(personRequest.getPhone_number());
        personUpdate.setAddress(personRequest.getAddress());
        personUpdate.setBirthdate(personRequest.getBirthDate());
        personRepository.save(personUpdate);
        return PersonResponse.builder()
                .id(personUpdate.getId())
                .full_name(personUpdate.getFull_name())
                .gender(personUpdate.getGender())
                .birthDate(personUpdate.getBirthdate())
                .address(personUpdate.getAddress())
                .build();
    }

}
