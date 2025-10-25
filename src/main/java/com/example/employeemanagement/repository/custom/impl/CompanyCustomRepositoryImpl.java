package com.example.employeemanagement.repository.custom.impl;

import com.example.employeemanagement.model.Company;
import com.example.employeemanagement.repository.custom.CompanyCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyCustomRepositoryImpl implements CompanyCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Company> findAllCompanyWithPagination(int page, int size) {
        String jpql = "Select c from Company c order by c.id asc ";
        TypedQuery<Company> query = entityManager.createQuery(jpql, Company.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }
}
