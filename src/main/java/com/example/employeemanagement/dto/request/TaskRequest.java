package com.example.employeemanagement.dto.request;

import com.example.employeemanagement.model.enums.Priority;
import com.example.employeemanagement.model.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class TaskRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @NotNull(message = "Status is required")
    private Status status;
    @NotNull(message = "Priority is required")
    private Priority priority;
    @NotNull(message = "Start time is required")
    private LocalDate start_time;
    @NotNull(message = "End time is required")
    private LocalDate end_time;
    private Long person_id;
    private Long project_id;
}
