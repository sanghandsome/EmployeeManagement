package com.example.employeemanagement.dto.response;


import com.example.employeemanagement.model.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse implements Serializable {
    private Long id;
    private String email;
    private boolean isActive;
    private String personName;
    private List<Roles> roleName;
}
