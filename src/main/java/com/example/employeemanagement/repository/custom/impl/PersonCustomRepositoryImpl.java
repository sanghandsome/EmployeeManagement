package com.example.employeemanagement.repository.custom.impl;

import com.example.employeemanagement.model.Person;
import com.example.employeemanagement.repository.custom.PersonCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonCustomRepositoryImpl implements PersonCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Person> findByCompanyIdWithPagination(Long companyId, int page, int size) {
        String jpql = "Select p from Person p where p.company.id = :companyId order by p.id asc ";
        TypedQuery<Person> query = entityManager.createQuery(jpql, Person.class);
        query.setParameter("companyId", companyId);
        query.setFirstResult(page *size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public List<Person> findAllPersonWithPagination(int page, int size) {
        String jpql = "Select p from Person p order by p.id asc ";
        TypedQuery<Person> query = entityManager.createQuery(jpql, Person.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }
}
