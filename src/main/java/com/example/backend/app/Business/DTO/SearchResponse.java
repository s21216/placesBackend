package com.example.backend.app.Business.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record SearchResponse<T>(
        @Schema(description = "Rozmiar strony", example = "100")
        Integer pageSize,

        @Schema(description = "Numer strony", example = "0")
        Integer pageNumber,

        @Schema(description = "Lista elementów")
        List<T> results
) {

}
