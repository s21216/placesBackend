package com.example.backend.app.Review.DTO;

import com.example.backend.app.ReviewReply.ReviewReply;

public record ReviewResponse (
        Long id,
        PostOwner postOwner,
        String businessId,
        String businessName,
        Integer score,
        String description,
        ReviewReply reply
) {
}
