package com.example.employeemanagement.repository.specification;

import com.example.employeemanagement.model.User;
import org.apache.catalina.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> searchByEmail(String email) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (email != null) {
                return criteriaBuilder.like(root.get("username"),"%"+email+"%");
            }
            else {
                return criteriaBuilder.conjunction();
            }
        };
    }
}
