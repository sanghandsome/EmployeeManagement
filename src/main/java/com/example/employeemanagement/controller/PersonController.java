package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.request.PersonRequest;
import com.example.employeemanagement.dto.response.ApiResponse;
import com.example.employeemanagement.dto.response.CountryResponse;
import com.example.employeemanagement.dto.response.PersonResponse;
import com.example.employeemanagement.service.PersonService;
import com.example.employeemanagement.service.impl.PersonServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
@Validated
public class PersonController {
    private final PersonService personService;

    @GetMapping
    public ResponseEntity<List<PersonResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<PersonResponse> personResponses= personService.findAllPersons(page, size);
        return ResponseEntity.ok(personResponses);
    }

    @GetMapping("/{id}")
    public ApiResponse<PersonResponse> findById(@PathVariable @Positive(message = "Id must be greater than 0") Long id){
        PersonResponse personResponse = personService.findPersonById(id);
        return ApiResponse.<PersonResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Person retrieved successfully")
                .data(personResponse)
                .build();
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<List<PersonResponse>> findByCompany(
            @PathVariable @Positive(message = "Id must be greater than 0") Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<PersonResponse> personResponses= personService.findAllPersonsByCompany(id, page, size);
        return ResponseEntity.ok(personResponses);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<PersonResponse> save(
            @RequestPart("person") @Valid PersonRequest personRequest,
            @RequestPart(value = "avatar", required = false) MultipartFile avatarFile) {

        PersonResponse personResponse = personService.createPerson(personRequest, avatarFile);
        return ApiResponse.<PersonResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Person created successfully")
                .data(personResponse)
                .build();
    }

    @PostMapping(value = "/{id}/upload-avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<String> uploadAvatar(
            @PathVariable Long id,
            @RequestPart("avatar") MultipartFile avatarFile) {

        String imageUrl = personService.updateAvatar(id,avatarFile);
        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Person changed image successfully")
                .data(imageUrl)
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<PersonResponse> update(
            @RequestPart("person") @Valid PersonRequest personRequest,
            @RequestPart(value = "avatar", required = false) MultipartFile avatarFile,
            @PathVariable @Positive(message = "Id must be greater than 0") Long id){
        PersonResponse personResponse = personService.updatePerson(personRequest, id, avatarFile);
        return ApiResponse.<PersonResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Person created successfully")
                .data(personResponse)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable @Positive(message = "Id must be greater than 0") Long id){
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Person created successfully")
                .data(null)
                .build();
    }
}
