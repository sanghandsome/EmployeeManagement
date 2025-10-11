package com.example.employeemanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CountryRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Nam is required")
    private String code;
    private String description;
}

