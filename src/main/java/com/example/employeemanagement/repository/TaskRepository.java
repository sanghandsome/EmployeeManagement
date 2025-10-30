package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Task;
import com.example.employeemanagement.repository.custom.TaskCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>, TaskCustomRepository {
    @Query("SELECT DISTINCT " +
            "t FROM Task t " +
            "LEFT JOIN FETCH t.project p " +
            "LEFT JOIN FETCH t.person per")
    List<Task> findAllWithRelation();
}
