package com.example.backend.app.ReviewReply;

import com.example.backend.app.Review.Review;
import com.example.backend.app.Review.ReviewRepository;
import com.example.backend.app.Review.ReviewService;
import com.example.backend.exceptions.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewReplyService {
    private final ReviewService reviewService;
    private final ReviewReplyRepository reviewReplyRepository;
    private final ReviewRepository reviewRepository;

    public ReviewReply createOrUpdateReviewReply(String businessId, Long reviewId, String description) {
        Review review = reviewService.getReviewById(reviewId);
        if (!Objects.equals(businessId, review.getBusiness().getFirebaseUid())) {
            throw new UnauthorizedException("Unauthorized");
        }
        ReviewReply reviewReply = review.getReviewReply();
        if (reviewReply == null) {
            reviewReply = new ReviewReply(review.getBusiness(), review, description);
            review.setReviewReply(reviewReply);
            reviewRepository.save(review);
        }
        reviewReply.setDescription(description);
        return reviewReplyRepository.save(reviewReply);
    }

    public void removeReviewReply(String businessId, Long reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        if (!Objects.equals(businessId, review.getBusiness().getFirebaseUid())) {
            throw new UnauthorizedException("Unauthorized");
        }
        review.setReviewReply(null);
        reviewRepository.save(review);
    }

}
