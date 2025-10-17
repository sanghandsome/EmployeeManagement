package com.example.employeemanagement.dto.response;


import com.example.employeemanagement.model.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private boolean isActive;
    private String personName;
    private List<Roles> roleName;
}
