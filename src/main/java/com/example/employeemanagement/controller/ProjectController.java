package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.request.ProjectRequest;
import com.example.employeemanagement.dto.response.ApiResponse;
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
    public ApiResponse<ProjectResponse> getProjectById(@PathVariable @Positive(message = "Id must be greater than 0") Long id){
        return ApiResponse.<ProjectResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Project retrieved successfully")
                .data(projectService.getProjectById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<ProjectResponse> createProject(@RequestBody @Valid ProjectRequest projectRequest) {
        ProjectResponse projectResponse = projectService.createProject(projectRequest);

        return ApiResponse.<ProjectResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Project created successfully")
                .data(projectResponse)
                .build();
    }

    @PatchMapping("/{id}")
    public  ApiResponse<ProjectResponse> updateProject(@RequestBody @Valid ProjectRequest projectRequest, @PathVariable @Positive(message = "Id must be greater than 0") Long id){
        ProjectResponse projectResponse = projectService.updateProject(id, projectRequest);
        return ApiResponse.<ProjectResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Project updated successfully")
                .data(projectResponse)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProject(@PathVariable @Positive Long id){
        projectService.deleteProjectById(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Project deleted successfully")
                .data(null)
                .build();
    }

}
