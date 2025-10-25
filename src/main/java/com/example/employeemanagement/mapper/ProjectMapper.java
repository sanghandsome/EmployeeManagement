package com.example.employeemanagement.mapper;

import com.example.employeemanagement.dto.request.ProjectRequest;
import com.example.employeemanagement.dto.response.ProjectResponse;
import com.example.employeemanagement.model.Company;
import com.example.employeemanagement.model.Person;
import com.example.employeemanagement.model.Project;
import com.example.employeemanagement.model.ProjectPerson;
import com.example.employeemanagement.repository.CompanyRepository;
import com.example.employeemanagement.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Log4j2
public class ProjectMapper {

    private final CompanyRepository companyRepository;
    private final PersonRepository personRepository;

    public ProjectResponse toProjectResponse(Project project) {

//        Set<Person> invalidPersonNames = new HashSet<>(personRepository.findAllById(project.getPersonIds()));

        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .code(project.getCode())
                .company_name(project.getCompany().getName())
                .person_name(project.getPersonNames())
//                .invalid_person_name(invalidPersonNames.stream().map(Person::getFull_name).collect(Collectors.toSet()))
                .build();
    }

    public Project toProject(ProjectRequest projectRequest) {
        Project project = new Project();

        Company companyCreate = companyRepository.findById(projectRequest.getCompany_id()).orElseThrow(() -> new RuntimeException("Company doesn't exist"));
        Set<Person> persons = new HashSet<>(personRepository.findAllById(projectRequest.getPerson_id()));

//        Set<Long> projectIds = projectRequest.getPerson_id();
        Set<Person> validPersons = persons.stream()
                .filter(p -> p.getCompany().getId().equals(companyCreate.getId()))
                .collect(Collectors.toSet());


//        Set<Long> invalidPersonIds = projectIds.stream()
//                .filter(id -> validPersons.stream().noneMatch(p -> p.getId().equals(id)))
//                .collect(Collectors.toSet());
//        project.setInvalidPersonIds(invalidPersonIds);

        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());
        project.setCode(projectRequest.getCode());
        project.setCompany(companyCreate);
        Set<ProjectPerson> projectPersons = new HashSet<>();
        for (Person person : validPersons){
            ProjectPerson projectPerson = new ProjectPerson();
            projectPerson.setPerson(person);
            projectPerson.setProject(project);
            projectPersons.add(projectPerson);
        }

        project.setProjectPersons(projectPersons);
        return project;
    }

    public void updateProject(Project project, ProjectRequest projectRequest){
        Company companyUpdate = companyRepository.findById(projectRequest.getCompany_id())
                .orElseThrow(() -> new RuntimeException("Company doesn't exist"));

        Set<Person> persons = new HashSet<>(personRepository.findAllById(projectRequest.getPerson_id()));

//        Set<Long> projectIds = projectRequest.getPerson_id();
        Set<Person> validPersons = persons.stream()
                .filter(p -> p.getCompany().getId().equals(companyUpdate.getId()))
                .collect(Collectors.toSet());


//        Set<Long> invalidPersonIds = projectIds.stream()
//                .filter(id -> validPersons.stream().noneMatch(p -> p.getId().equals(id)))
//                .collect(Collectors.toSet());
//        project.setInvalidPersonIds(invalidPersonIds);

        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());
        project.setCode(projectRequest.getCode());
        project.setCompany(companyUpdate);

        project.getProjectPersons().removeIf(pp -> !persons.contains(pp.getPerson()));

        for (Person person : validPersons){
            boolean exists = project.getProjectPersons().stream()
                    .anyMatch(pp -> pp.getPerson().getId().equals(person.getId()));
            if (!exists) {
                ProjectPerson projectPerson = new ProjectPerson();
                projectPerson.setPerson(person);
                projectPerson.setProject(project);
                project.getProjectPersons().add(projectPerson);
            }
        }
    }
}
