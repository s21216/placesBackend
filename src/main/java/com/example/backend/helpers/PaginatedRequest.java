package com.example.backend.helpers;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class PaginatedRequest {
    private Integer pageSize = 10;
    private Integer pageNumber = 0;
    private String orderBy;
    private Sort.Direction sortOrder = Sort.Direction.ASC;
}
