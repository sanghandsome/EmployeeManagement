package com.example.employeemanagement.mapper;

import com.example.employeemanagement.dto.request.TaskRequest;
import com.example.employeemanagement.dto.response.TaskResponse;
import com.example.employeemanagement.model.Person;
import com.example.employeemanagement.model.Project;
import com.example.employeemanagement.model.Task;
import com.example.employeemanagement.repository.PersonRepository;
import com.example.employeemanagement.repository.ProjectRepository;
import com.example.employeemanagement.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskMapper {
    private final TaskRepository taskRepository;
    private final PersonRepository personRepository;
    private final ProjectRepository projectRepository;

    public TaskResponse toTaskResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .start_time(task.getStart_time())
                .end_time(task.getEnd_time())
                .person_name(task.getPerson().getFull_name())
                .project_name(task.getProject().getName())
                .build();
    }

    public Task toTask(TaskRequest taskRequest) {
        Person person = personRepository.findById(taskRequest
                .getPerson_id())
                .orElseThrow(() -> new RuntimeException("Person does not exist"));
        Project project = projectRepository.findById(taskRequest
                .getProject_id())
                .orElseThrow(() -> new RuntimeException("Project does not exist"));
        Long company_id_preson = person.getCompany().getId();
        Long company_id_project = project.getCompany().getId();
        if (company_id_project != company_id_preson) {
            throw new RuntimeException("Company id doesn't match project id preson");
        }
        Task task = new Task();
        task.setName(taskRequest.getName());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(taskRequest.getStatus());
        task.setPriority(taskRequest.getPriority());
        task.setStart_time(taskRequest.getStart_time());
        task.setEnd_time(taskRequest.getEnd_time());
        task.setPerson(person);
        task.setProject(project);
        return task;
    }

    public void updateTask(TaskRequest taskRequest, Task task) {
        Person person = personRepository.findById(taskRequest
                        .getPerson_id())
                .orElseThrow(() -> new RuntimeException("Person does not exist"));
        Project project = projectRepository.findById(taskRequest
                        .getProject_id())
                .orElseThrow(() -> new RuntimeException("Project does not exist"));
        Long company_id_preson = person.getCompany().getId();
        Long company_id_project = project.getCompany().getId();
        if (company_id_project != company_id_preson) {
            throw new RuntimeException("Company id doesn't match project id preson");
        }
        task.setName(taskRequest.getName());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(taskRequest.getStatus());
        task.setPriority(taskRequest.getPriority());
        task.setStart_time(taskRequest.getStart_time());
        task.setEnd_time(taskRequest.getEnd_time());
        task.setPerson(person);
        task.setProject(project);
    }
}
