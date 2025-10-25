package com.example.employeemanagement.repository.custom.impl;

import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.repository.custom.DepartmentCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DepartmentCustomRepositoryImpl implements DepartmentCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Department> findDepartmentByCompanyWithPagination(Long company_id, int page, int size) {
        String jpql = "select d from Department d where d.company.id = :companyId order by d.id asc";
        TypedQuery<Department> query = entityManager.createQuery(jpql, Department.class);
        query.setParameter("companyId", company_id);
        query.setFirstResult( page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public Boolean existsByIdAndCompanyId(Long departmentId, Long companyId) {
        String jpql = "SELECT CASE WHEN COUNT(d) > 0 THEN TRUE ELSE FALSE END " +
                "FROM Department d WHERE d.id = :departmentId AND d.company.id = :companyId";

        TypedQuery<Boolean> query = entityManager.createQuery(jpql, Boolean.class);
        query.setParameter("departmentId", departmentId);
        query.setParameter("companyId", companyId);

        return query.getSingleResult();
    }
}
