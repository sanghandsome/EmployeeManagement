package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Role;
import com.example.employeemanagement.model.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(Roles roles);

}
