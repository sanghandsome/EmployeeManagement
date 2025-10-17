package com.example.employeemanagement.mapper;

import com.example.employeemanagement.dto.request.PersonRequest;
import com.example.employeemanagement.dto.response.PersonResponse;
import com.example.employeemanagement.model.Company;
import com.example.employeemanagement.model.Person;
import com.example.employeemanagement.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonMapper {

    private final CompanyRepository companyRepository;

    public PersonResponse toPersonResponse(Person person) {
        if (person == null) {
            return null;
        }
        return PersonResponse.builder()
                .id(person.getId())
                .full_name(person.getFull_name())
                .gender(person.getGender())
                .address(person.getAddress())
                .birthDate(person.getBirthdate())
                .phone_number(person.getPhone_number())
                .companyName(person.getCompany().getName())
                .build();
    }


    public Person toPersonFromPersonRequest(PersonRequest personRequest) {
        if (personRequest == null) {
            return null;
        }
        Company company = companyRepository.findById(personRequest.getCompanyId()).orElseThrow(() -> new RuntimeException("Company not found"));
        Person person = new Person();
        person.setFull_name(personRequest.getFull_name());
        person.setGender(personRequest.getGender());
        person.setAddress(personRequest.getAddress());
        person.setBirthdate(personRequest.getBirthDate());
        person.setPhone_number(personRequest.getPhone_number());
        person.setCompany(company);
        return person;
    }

    public void updatePerson(Person person, PersonRequest personRequest) {
        if (person == null || personRequest == null) {
            return;
        }
        Company company = companyRepository.findById(personRequest.getCompanyId()).orElseThrow(() -> new RuntimeException("Company not found"));
        person.setFull_name(personRequest.getFull_name());
        person.setGender(personRequest.getGender());
        person.setAddress(personRequest.getAddress());
        person.setBirthdate(personRequest.getBirthDate());
        person.setPhone_number(personRequest.getPhone_number());
        person.setCompany(company);
    }
}