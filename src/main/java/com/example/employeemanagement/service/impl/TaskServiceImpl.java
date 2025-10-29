package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.dto.request.TaskRequest;
import com.example.employeemanagement.dto.response.TaskResponse;
import com.example.employeemanagement.mapper.TaskMapper;
import com.example.employeemanagement.model.Person;
import com.example.employeemanagement.model.Project;
import com.example.employeemanagement.model.Task;
import com.example.employeemanagement.repository.PersonRepository;
import com.example.employeemanagement.repository.ProjectRepository;
import com.example.employeemanagement.repository.TaskRepository;
import com.example.employeemanagement.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final PersonRepository personRepository;
    private final ProjectRepository projectRepository;

    @Override
    public List<TaskResponse> searchTask(String name, int page, int size) {
        List<Task> exactMatch = taskRepository.searchExactWithPagination(name,page,size);
//        List<Task> exactMatch = taskRepository.findByName(name);
        if (!exactMatch.isEmpty()) {
            return exactMatch
                    .stream()
                    .map(taskMapper::toTaskResponse)
                    .toList();
        }

        return taskRepository.searchWithPagination(name, page, size)
                .stream()
                .map(taskMapper::toTaskResponse)
                .toList();
    }

    @Override
    public List<TaskResponse> filterTasks(Long company_id,
                                          Long project_id,
                                          Long person_id,
                                          String status,
                                          String priority,
                                          int page, int size) {
        return taskRepository.filterWithPagination(company_id, project_id, person_id, status, priority, page, size)
                .stream()
                .map(taskMapper::toTaskResponse)
                .toList();
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        return taskMapper
                .toTaskResponse(taskRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Task doesn't exist")));
    }

    @Override
    public TaskResponse createTask(TaskRequest taskRequest) {
        Task task = taskMapper.toTask(taskRequest);
        taskRepository.save(task);
        return taskMapper.toTaskResponse(task);
    }

    @Override
    public TaskResponse updateTask(TaskRequest taskRequest, Long id) {
        Person person = personRepository.findById(taskRequest.getPerson_id())
                .orElseThrow(()->new RuntimeException("Person doesn't exist"));
        Long company_id_preson = person.getCompany().getId();
        Project project = projectRepository.findById(taskRequest.getProject_id())
                .orElseThrow(()->new RuntimeException("Project doesn't exist"));
        Long company_id_project = project.getCompany().getId();
        if (company_id_project != company_id_preson) {
            throw new RuntimeException("Company id doesn't match project id preson");
        }
        Task task = taskRepository.findById(id).orElseThrow(()->new RuntimeException("Task doesn't exist"));
        taskMapper.updateTask(taskRequest,task);
        taskRepository.save(task);
        return taskMapper.toTaskResponse(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
