package com.example.employeemanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequest {

    @NotBlank(message =  "Code is required")
    private String code;

    @NotBlank(message =  "Name is required")
    private String name;

    private Long parent_id;

    private Long company_id;
}
