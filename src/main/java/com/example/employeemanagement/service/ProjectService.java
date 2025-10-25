package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.request.ProjectRequest;
import com.example.employeemanagement.dto.response.ProjectResponse;
import com.example.employeemanagement.model.Company;
import com.example.employeemanagement.model.Person;
import com.example.employeemanagement.model.Project;
import java.util.List;


public interface ProjectService {

    public List<ProjectResponse> getAllProjectsByCompanyId(Long companyId,int page, int size);

    public List<ProjectResponse> getAllProjectsByPersonId(Long personId,int page, int size);

    public ProjectResponse getProjectById(Long projectId);

    public ProjectResponse createProject(ProjectRequest projectRequest);

    public ProjectResponse updateProject(Long projectId, ProjectRequest projectRequest);

    public void deleteProjectById(Long projectId);
}
