package com.example.employeemanagement.repository.custom.impl;

import com.example.employeemanagement.model.Project;
import com.example.employeemanagement.repository.custom.ProjectCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ProjectCustomRepositoryImpl implements ProjectCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Project> findByCompanyIdWithPagination(Long companyId, int page, int size) {
        String jpql = "SELECT p FROM Project p WHERE p.company.id = :companyId ORDER BY p.id ASC";
        TypedQuery<Project> query = entityManager.createQuery(jpql, Project.class);
        query.setParameter("companyId", companyId);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public List<Project> findByPersonIdWithPagination(Long personId, int page, int size) {
        String jpql = "SELECT DISTINCT p FROM Project p JOIN p.projectPersons pp WHERE pp.person.id = :personId ORDER BY p.id ASC";
        TypedQuery<Project> query = entityManager.createQuery(jpql, Project.class);
        query.setParameter("personId", personId);
        query.setFirstResult(page * size); // offset
        query.setMaxResults(size);         // limit
        return query.getResultList();
    }
}
