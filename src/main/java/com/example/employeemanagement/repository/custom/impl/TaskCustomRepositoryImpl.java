package com.example.employeemanagement.repository.custom.impl;

import com.example.employeemanagement.model.Task;
import com.example.employeemanagement.model.enums.Priority;
import com.example.employeemanagement.model.enums.Status;
import com.example.employeemanagement.repository.custom.TaskCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskCustomRepositoryImpl implements TaskCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Task> filterWithPagination(Long company_id,
                                           Long project_id,
                                           Long person_id,
                                           String status,
                                           String priority,
                                           int page, int size) {
        StringBuilder jpql = new StringBuilder("SELECT t FROM Task t WHERE 1=1");
        if(company_id!=null){
            jpql.append(" AND (t.project.company.id = :company_id AND t.person.company.id = :company_id)");
        }
        if(project_id!=null){
            jpql.append(" AND t.project.id=:project_id");
        }
        if(person_id!=null){
            jpql.append(" AND t.person.id=:person_id");
        }
        if(status!=null){
            jpql.append(" AND t.status=:status");
        }
        if(priority!=null){
            jpql.append(" AND t.priority=:priority");
        }
        jpql.append(" ORDER BY t.id ASC");
        TypedQuery<Task> query = entityManager.createQuery(jpql.toString(),Task.class);
        query.setFirstResult(page*size);
        query.setMaxResults(size);
        if(company_id!=null){query.setParameter("company_id",company_id);}
        if(project_id!=null){query.setParameter("project_id",project_id);}
        if(person_id!=null){query.setParameter("person_id",person_id);}
        if(status!=null){
            Status statusEnum = Status.valueOf(status.toUpperCase());
            query.setParameter("status",statusEnum);
        }
        if(priority!=null){
            Priority priorityEnum = Priority.valueOf(priority.toUpperCase());
            query.setParameter("priority",priorityEnum);
        }
        return query.getResultList();
    }

    @Override
    public List<Task> searchWithPagination(String name, int page, int size) {
        StringBuilder sql = new StringBuilder("SELECT * FROM tasks t ");

        if (name != null) {
            sql.append("WHERE MATCH(t.name) AGAINST(:keyword IN NATURAL LANGUAGE MODE) ");
        }

        sql.append("ORDER BY t.id ASC");

        Query query = entityManager.createNativeQuery(sql.toString(), Task.class);

        if (name != null) {
            query.setParameter("keyword", name);
        }

        query.setFirstResult(page * size);
        query.setMaxResults(size);
        System.out.println("SQL: " + sql.toString());
        System.out.println(name);
        return query.getResultList();
    }

    @Override
    public List<Task> searchExactWithPagination(String name, int page, int size) {
        String jpql = "SELECT t FROM Task t WHERE t.name=:keyword  ORDER BY t.id ASC";
        TypedQuery<Task> query = entityManager.createQuery(jpql,Task.class);
        query.setParameter("keyword", name);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public List<Task> findAllWithRelation() {
        String jpql = "SELECT DISTINCT t " +
                "FROM Task t " +
                "LEFT JOIN FETCH t.project p " +
                "LEFT JOIN FETCH t.person per";
        TypedQuery<Task> query = entityManager.createQuery(jpql,Task.class);
        return query.getResultList();
    }
}
