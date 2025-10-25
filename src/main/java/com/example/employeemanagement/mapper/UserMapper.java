package com.example.employeemanagement.mapper;

import com.example.employeemanagement.dto.request.UserRequest;
import com.example.employeemanagement.dto.response.UserResponse;
import com.example.employeemanagement.model.Person;
import com.example.employeemanagement.model.Role;
import com.example.employeemanagement.model.User;
import com.example.employeemanagement.model.UserRole;
import com.example.employeemanagement.model.enums.Roles;
import com.example.employeemanagement.repository.PersonRepository;
import com.example.employeemanagement.repository.RoleRepository;
import com.example.employeemanagement.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .isActive(user.isActive())
                .personName(user.getPerson().getFull_name())
                .roleName(user.getRoles().stream()
                        .map(Role::getRole)
                        .collect(Collectors.toList()))
                .build();
    }

    public User toUser(UserRequest userRequest) {
        if (userRequest == null) {
            return null;
        }
        Person person = personRepository.findById(userRequest.getPersonId())
                .orElseThrow(() -> new RuntimeException("Person not found"));;
        Set<Role> roles;
        if (userRequest.getRoleId() == null || userRequest.getRoleId().isEmpty()) {
            Role defaultRole = roleRepository.findByRole(Roles.USER)
                    .orElseThrow(() -> new RuntimeException("Default USER role not found"));
            roles = new HashSet<>();
            roles.add(defaultRole);
        } else {
            roles = new HashSet<>(roleRepository.findAllById(userRequest.getRoleId()));
        }

        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setActive(userRequest.isActive());
        user.setPassword(userRequest.getPassword());
        user.setPerson(person);
        Set<UserRole> userRoles = new HashSet<>();
        for (Role role : roles) {
            UserRole ur = new UserRole();
            ur.setRole(role);
            ur.setUser(user);
            userRoles.add(ur);
        }
        user.setUserRoles(userRoles);
        return user;
    }

    public User updateUser(UserRequest userRequest, User user) {
        if (userRequest == null) {
            return null;
        }
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(userRequest.getRoleId()));
        Person person = personRepository.findById(userRequest.getPersonId())
                .orElseThrow(() -> new RuntimeException("Person not found"));
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setPerson(person);
        user.setActive(userRequest.isActive());
        user.getUserRoles().clear();

        for(Role role : roles) {
            UserRole ur = new UserRole();
            ur.setRole(role);
            ur.setUser(user);
            user.getUserRoles().add(ur);
        }

        return user;
    }
}