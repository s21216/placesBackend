package com.example.backend.app.Business.DTO;

import com.example.backend.app.Business.Cost;

public record SearchFilters(
        Cost cost,
        Double longitude,
        Double latitude,
        Double distance
) {
}
