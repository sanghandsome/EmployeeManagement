package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.dto.request.PersonRequest;
import com.example.employeemanagement.dto.response.PersonResponse;
import com.example.employeemanagement.mapper.PersonMapper;
import com.example.employeemanagement.model.Person;
import com.example.employeemanagement.repository.PersonRepository;
import com.example.employeemanagement.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    // ======================== FIND METHODS ========================
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

    // ======================== UPLOAD AVATAR ========================
    @Override
    public String uploadAvatar(MultipartFile avatarFile) {
        try {
            String uploadDir = System.getProperty("user.dir") + "/uploads/avatars/";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = UUID.randomUUID() + "_" + avatarFile.getOriginalFilename();

            Path filePath = Paths.get(uploadDir + fileName);
            Files.copy(avatarFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/avatars/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Upload avatar failed", e);
        }
    }

    // ======================== CREATE PERSON ========================
    @Override
    public PersonResponse createPerson(PersonRequest personRequest, MultipartFile avatarFile) {
        String avatarPath = null;
        if (avatarFile != null && !avatarFile.isEmpty()) {
            avatarPath = uploadAvatar(avatarFile);
        }

        personRequest.setAvatar(avatarPath);
        Person personCreate = personMapper.toPersonFromPersonRequest(personRequest);
        personRepository.save(personCreate);
        return personMapper.toPersonResponse(personCreate);
    }

    // ======================== UPDATE PERSON ========================
    @Override
    public PersonResponse updatePerson(PersonRequest personRequest, Long id, MultipartFile avatarFile) {
        Person personUpdate = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person does not exist"));

        String avatarPath = null;
        if (avatarFile != null && !avatarFile.isEmpty()) {
            avatarPath = uploadAvatar(avatarFile);
        }

        personRequest.setAvatar(avatarPath);
        personMapper.updatePerson(personUpdate, personRequest);
        personRepository.save(personUpdate);
        return personMapper.toPersonResponse(personUpdate);
    }

    // ======================== UPDATE AVATAR RIÊNG ========================
    @Override
    public String updateAvatar(Long id, MultipartFile avatarFile) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        String path = uploadAvatar(avatarFile);
        person.setAvatar(path);
        personRepository.save(person);
        return path;
    }

    // ======================== DELETE ========================
    @Override
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
}