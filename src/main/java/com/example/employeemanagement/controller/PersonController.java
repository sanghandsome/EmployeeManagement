package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.request.PersonRequest;
import com.example.employeemanagement.dto.response.PersonResponse;
import com.example.employeemanagement.service.PersonService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
@Validated
public class PersonController {
    private final PersonService personService;

    @GetMapping
    public ResponseEntity<List<PersonResponse>> findAll() {
        List<PersonResponse> personResponses= personService.findAllPersons();
        return ResponseEntity.ok(personResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResponse> findById(@PathVariable @Positive(message = "Id must be greater than 0") Long id){
        PersonResponse personResponse = personService.findPersonById(id);
        return ResponseEntity.ok(personResponse);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<List<PersonResponse>> findByCompany(@PathVariable @Positive(message = "Id must be greater than 0") Long id) {
        List<PersonResponse> personResponses= personService.findAllPersonsByCompany(id);
        return ResponseEntity.ok(personResponses);
    }

    @PostMapping
    public ResponseEntity<PersonResponse> save(@RequestBody @Valid PersonRequest personRequest){
        PersonResponse personResponse = personService.createPerson(personRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(personResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PersonResponse> update(@RequestBody @Valid PersonRequest personRequest, @PathVariable @Positive(message = "Id must be greater than 0") Long id){
        PersonResponse personResponse = personService.updatePerson(personRequest, id);
        return ResponseEntity.ok(personResponse);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Positive(message = "Id must be greater than 0") Long id){
        personService.deletePerson(id);
    }
}
