package com.example.employeemanagement.dto.response;

import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private String company_name;
    private Set<String> person_name;
//    private Set<String> invalid_person_name;
}
