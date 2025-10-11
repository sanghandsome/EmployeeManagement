package com.example.employeemanagement.dto.response;

import com.example.employeemanagement.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonResponse {
    private Long id;
    private String full_name;
    private Gender gender;
    private LocalDate birthDate;
    private String address;

}
