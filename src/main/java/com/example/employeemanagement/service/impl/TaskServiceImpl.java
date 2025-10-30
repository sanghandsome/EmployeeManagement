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
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
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

    @Override
    public ByteArrayInputStream exportAllTasksToExcel() throws IOException {
        List<Task> tasks = taskRepository.findAllWithRelation();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Tasks");


            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);


            CreationHelper createHelper = workbook.getCreationHelper();
            CellStyle dateCellStyle = workbook.createCellStyle();
            short dateFormat = createHelper.createDataFormat().getFormat("yyyy-mm-dd HH:mm:ss");
            dateCellStyle.setDataFormat(dateFormat);

            // Header row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Project", "Description", "Start Time", "End Time", "Priority", "Status", "Person"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            // Fill data
            int rowIdx = 1;
            for (Task t : tasks) {
                Row row = sheet.createRow(rowIdx++);

                String projectName = t.getProject() != null ? t.getProject().getName() : "";
                row.createCell(0).setCellValue(projectName);

                row.createCell(1).setCellValue(t.getDescription() != null ? t.getDescription() : "");

                if (t.getStart_time() != null) {
                    Cell startCell = row.createCell(2);
                    startCell.setCellValue(t.getStart_time().format(formatter));
                    startCell.setCellStyle(dateCellStyle);
                } else {
                    row.createCell(2).setCellValue("");
                }

                // End Time
                if (t.getEnd_time() != null) {
                    Cell startCell = row.createCell(3);
                    startCell.setCellValue(t.getEnd_time().format(formatter));
                    startCell.setCellStyle(dateCellStyle);
                } else {
                    row.createCell(3).setCellValue("");
                }

                row.createCell(4).setCellValue(t.getPriority() != null ? t.getPriority().name() : "");

                row.createCell(5).setCellValue(t.getStatus() != null ? t.getStatus().name() : "");

                String personName = t.getPerson() != null ? t.getPerson().getFull_name() : "";
                row.createCell(6).setCellValue(personName);
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
