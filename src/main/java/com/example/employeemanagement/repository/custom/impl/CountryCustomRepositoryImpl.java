package com.example.employeemanagement.repository.custom.impl;

import com.example.employeemanagement.model.Company;
import com.example.employeemanagement.model.Country;
import com.example.employeemanagement.repository.custom.CountryCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CountryCustomRepositoryImpl implements CountryCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Country> findAllCountryWithPagination(int page, int size) {
        String jpql = "Select c from Country c order by c.id desc ";
        TypedQuery<Country> query = entityManager.createQuery(jpql, Country.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }
}
