package com.example.employeemanagement.repository.custom;
import com.example.employeemanagement.model.Project;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProjectCustomRepository {
    List<Project> findByCompanyIdWithPagination(Long companyId, int page, int size);
    List<Project> findByPersonIdWithPagination(Long personId, int page, int size);
}
