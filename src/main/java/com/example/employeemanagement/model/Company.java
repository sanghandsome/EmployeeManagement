package com.example.employeemanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(nullable = false, unique = true)
    @NotNull
    private String code;

    @Column(nullable = false)
    @NotNull
    private String address;

    @OneToMany(mappedBy = "company" ,cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Person> persons;
}
