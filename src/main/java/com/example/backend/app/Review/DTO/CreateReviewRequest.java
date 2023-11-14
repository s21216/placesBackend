package com.example.backend.app.Review.DTO;

public record CreateReviewRequest(
        String businessId,
        Integer rating,
        String description
){
}
