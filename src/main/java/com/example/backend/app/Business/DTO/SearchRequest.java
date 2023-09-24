package com.example.backend.app.Business.DTO;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class SearchRequest {
    private Integer pageSize = 10;
    private Integer pageNumber = 0;
    private String orderBy;
    private Sort.Direction sortOrder = Sort.Direction.ASC;
    private SearchFilters filters;
}
