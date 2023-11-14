package com.example.backend.app.Review.DTO;

public record UpdateReviewRequest(
        String reviewId,
        String businessId,
        Integer rating,
        String description
){
}
