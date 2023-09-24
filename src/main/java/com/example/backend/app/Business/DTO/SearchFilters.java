package com.example.backend.app.Business.DTO;

import com.example.backend.app.Business.Cost;
import org.hibernate.search.engine.spatial.GeoPoint;

public record SearchFilters(
        Cost cost,
        GeoPoint coordinates,
        Double distance
) {
}
