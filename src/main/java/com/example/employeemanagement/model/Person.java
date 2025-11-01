package com.example.employeemanagement.model;

import com.example.employeemanagement.model.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.context.annotation.EnableMBeanExport;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "persons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String full_name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthdate;

    private String phone_number;

    private String address;

    private String avatar;
    @OneToOne( mappedBy = "person")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "company_id")
    @NotNull(message = "Company is required")
    private Company company;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectPerson> projectPersons = new HashSet<>();

    @OneToMany(mappedBy = "person",cascade = CascadeType.ALL)
    private List<Task> tasks;

}
