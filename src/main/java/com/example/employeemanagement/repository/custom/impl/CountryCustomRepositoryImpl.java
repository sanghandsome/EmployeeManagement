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
    public List<Country> findAllCountryWithPagination(int page, int size, String keyword) {
        String jpql = "SELECT c FROM Country c";

        if (keyword != null && !keyword.isEmpty()) {
            jpql += " WHERE LOWER(c.name) LIKE :kw OR LOWER(c.code) LIKE :kw";
        }

        jpql += " ORDER BY c.id DESC";

        TypedQuery<Country> query = entityManager.createQuery(jpql, Country.class);

        if (keyword != null && !keyword.isEmpty()) {
            query.setParameter("kw", "%" + keyword.toLowerCase() + "%");
        }

        query.setFirstResult(page * size);
        query.setMaxResults(size);

        return query.getResultList();
    }
}
