package com.example.employeemanagement.repository.custom;

import com.example.employeemanagement.model.Task;

import java.util.List;

public interface TaskCustomRepository {
    List<Task> filterWithPagination(Long company_id,Long project_id,Long person_id,String status,String priority, int page, int size);
    List<Task> searchWithPagination(String name, int page, int size);
    List<Task> searchExactWithPagination(String name, int page, int size);
}
