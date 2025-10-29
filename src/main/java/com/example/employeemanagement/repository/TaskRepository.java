package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Task;
import com.example.employeemanagement.repository.custom.TaskCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>, TaskCustomRepository {}
