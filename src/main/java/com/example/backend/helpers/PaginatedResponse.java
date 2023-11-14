package com.example.backend.helpers;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record PaginatedResponse<T>(
        @Schema(description = "Rozmiar strony", example = "100")
        Integer pageSize,

        @Schema(description = "Numer strony", example = "0")
        Integer pageNumber,

        @Schema(description = "Liczba elementów na stronie", example = "5")
        Integer numberOfElements,

        @Schema(description = "Liczba wszystkich elementów", example = "5")
        Long totalNumberOfElements,

        @Schema(description = "Liczba stron", example = "5")
        Integer totalPages,

        @Schema(description = "Lista elementów")
        List<T> results
) {

}
