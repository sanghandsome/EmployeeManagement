package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.request.TaskRequest;
import com.example.employeemanagement.dto.response.TaskResponse;
import com.example.employeemanagement.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tasks")
@RequiredArgsConstructor
@Validated
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/search")
    public ResponseEntity<List<TaskResponse>> searchTasks(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
            ) {
        return ResponseEntity.ok(taskService.searchTask(name, page, size));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<TaskResponse>> filterTasks(
            @RequestParam(required = false) Long company_id,
            @RequestParam(required = false) Long project_id,
            @RequestParam(required = false) Long person_id,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        return ResponseEntity.ok(taskService.filterTasks(company_id, project_id, person_id, status, priority, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable @Positive(message = "Id must be greater than 0") Long id){
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Valid TaskRequest taskRequest){
        return ResponseEntity.ok(taskService.createTask(taskRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @RequestBody @Valid TaskRequest taskRequest,
            @PathVariable @Positive(message = "Id must be greater than 0") Long id){
        return ResponseEntity.ok(taskService.updateTask(taskRequest, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable @Positive(message = "Id must be greater than 0") Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
