package com.example.employeemanagement.dto.response;

import com.example.employeemanagement.model.enums.Priority;
import com.example.employeemanagement.model.enums.Status;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class TaskResponse {
    private Long id;
    private String name;
    private String description;
    private Status status;
    private Priority priority;
    private LocalDate start_time;
    private LocalDate end_time;
    private String person_name;
    private String project_name;
}
