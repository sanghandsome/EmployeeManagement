package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.request.TaskRequest;
import com.example.employeemanagement.dto.response.TaskResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface TaskService {
    public List<TaskResponse> searchTask(String name, int page, int size);

    public List<TaskResponse> filterTasks(Long company_id,
                                  Long project_id,
                                  Long person_id,
                                  String status,
                                  String priority,
                                  int page, int size);

    public TaskResponse getTaskById(Long id);
    public TaskResponse createTask(TaskRequest taskRequest);
    public TaskResponse updateTask(TaskRequest taskRequest, Long id);
    public void deleteTask(Long id);
    public ByteArrayInputStream exportAllTasksToExcel() throws IOException;
}
