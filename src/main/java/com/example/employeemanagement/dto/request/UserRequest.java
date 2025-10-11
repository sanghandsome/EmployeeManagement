package com.example.employeemanagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format ")
    private String email;

    @NotBlank(message = "Password is required")
    @Length(min = 6, max = 20 , message = "Password must be between 6 and 20 characters")
    private String password;

    private boolean isActive;

    private Long personId;
}
