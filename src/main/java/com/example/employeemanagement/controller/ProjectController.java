package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.request.ProjectRequest;
import com.example.employeemanagement.dto.response.ProjectResponse;
import com.example.employeemanagement.service.ProjectService;
import com.example.employeemanagement.service.impl.ProjectServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectServiceImpl projectService;

    @GetMapping("/{comapyId}/company")
    public ResponseEntity<List<ProjectResponse>> getProjectByCompany(
            @PathVariable @Positive(message = "Id must be greater than 0") Long comapyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok(projectService.getAllProjectsByCompanyId(comapyId, page, size));
    }

    @GetMapping("/{personId}/person")
    public ResponseEntity<List<ProjectResponse>> getProjectByPerson(
            @PathVariable @Positive(message = "Id must be greater than 0") Long personId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok(projectService.getAllProjectsByPersonId(personId,page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable @Positive(message = "Id must be greater than 0") Long id){
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody @Valid ProjectRequest projectRequest) {
        ProjectResponse projectResponse = projectService.createProject(projectRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "Some persons were not added because they do not belong to this company",
                        "project", projectResponse
                ));
    }

    @PatchMapping("/{id}")
    public  ResponseEntity<?> updateProject(@RequestBody @Valid ProjectRequest projectRequest, @PathVariable @Positive(message = "Id must be greater than 0") Long id){
        ProjectResponse projectResponse = projectService.updateProject(id, projectRequest);
        return ResponseEntity.ok(Map.of(
                "message", "Some persons were not added because they do not belong to this company",
                "project", projectResponse
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable @Positive Long id){
        projectService.deleteProjectById(id);
        return ResponseEntity.ok().build();
    }

}
