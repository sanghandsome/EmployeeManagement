package com.example.employeemanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectPerson> projectPersons = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> tasks;

    public Set<String> getPersonNames() {
        Set<String> personNames = new HashSet<>();
        for (ProjectPerson projectPerson : projectPersons) {
            personNames.add(projectPerson.getPerson().getFull_name());
        }
        return personNames;
    }


//    @Transient
//    public Set<Long> getPersonIds() {
//        Set<Long> personIds = new HashSet<>();
//        for (ProjectPerson projectPerson : projectPersons) {
//            personIds.add(projectPerson.getPerson().getId());
//        }
//        return personIds;
//    }

//    @Transient
//    private Set<Long> invalidPersonIds = new HashSet<>();
}