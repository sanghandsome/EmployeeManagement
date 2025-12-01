package com.example.employeemanagement.dto.response;

import lombok.*;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PageResponse<T> {
    private int currentPages;
    private int pageSizes;
    private int totalPages;
    private long totalElements;

    @Builder.Default
    private List<T> data = Collections.emptyList();
}
