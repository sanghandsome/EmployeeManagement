package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.dto.request.ProjectRequest;
import com.example.employeemanagement.dto.response.ProjectResponse;
import com.example.employeemanagement.mapper.ProjectMapper;
import com.example.employeemanagement.model.Company;
import com.example.employeemanagement.model.Person;
import com.example.employeemanagement.model.Project;
import com.example.employeemanagement.repository.CompanyRepository;
import com.example.employeemanagement.repository.PersonRepository;
import com.example.employeemanagement.repository.ProjectRepository;
import com.example.employeemanagement.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final CompanyRepository companyRepository;
    private final PersonRepository personRepository;

    @Override
    public List<ProjectResponse> getAllProjectsByCompanyId(Long companyId, int page, int size){
        List<Project> projects = projectRepository.findByCompanyIdWithPagination(companyId, page, size);
        return projects.stream()
                .map(projectMapper::toProjectResponse)
                .toList();
    }

    @Override
    public List<ProjectResponse> getAllProjectsByPersonId(Long personId, int page, int size){
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person does not exist"));

        List<Project> projects = projectRepository.findByPersonIdWithPagination(personId, page, size);
        return projects.stream()
                .map(projectMapper::toProjectResponse)
                .toList();
    }

    @Override
    public ProjectResponse getProjectById(Long projectId){
        return projectMapper.toProjectResponse(projectRepository.findById(projectId).orElseThrow(()->new RuntimeException("Project does not exist")));
    }

    @Override
    public ProjectResponse createProject(ProjectRequest projectRequest){
        return projectMapper.toProjectResponse(projectRepository.save(projectMapper.toProject(projectRequest)));
    }

    @Override
    public ProjectResponse updateProject(Long projectId, ProjectRequest projectRequest){
        Project project = projectRepository.findById(projectId).orElseThrow(()->new RuntimeException("Project does not exist"));
        projectMapper.updateProject(project, projectRequest);
        projectRepository.save(project);
        return projectMapper.toProjectResponse(project);
    }

    @Override
    public void deleteProjectById(Long projectId){
        projectRepository.deleteById(projectId);
    }
}
