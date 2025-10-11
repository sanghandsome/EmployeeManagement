package com.example.employeemanagement.dto.request;

import com.example.employeemanagement.model.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonRequest {

    @NotBlank(message = "Full Name is required")
    private String full_name;

    @NotBlank(message = "Gender is required")
    private Gender gender;

    private LocalDate birthDate;

    @NotBlank(message = "Phone Number is required")
    @Pattern(
            regexp = "^[0-9]{10,11}$",
            message = "Phone number must be 10 or 11 digits"
    )
    private String phone_number;

    @NotBlank(message = "Address is required")
    private String address;
}
