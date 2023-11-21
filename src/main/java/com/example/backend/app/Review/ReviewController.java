package com.example.backend.app.Review;

import com.example.backend.app.Review.DTO.CreateReviewRequest;
import com.example.backend.app.Review.DTO.PostOwner;
import com.example.backend.app.Review.DTO.ReviewResponse;
import com.example.backend.exceptions.NotFoundException;
import com.example.backend.helpers.Authentication;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PutMapping
    ReviewResponse createOrUpdateReview(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CreateReviewRequest request) throws FirebaseAuthException {
        Review review = reviewService.createOrUpdateReview(Authentication.extractUid(authorizationHeader), request.businessId(), request.score(), request.description());
        return new ReviewResponse(
                review.getId(),
                new PostOwner(review.getUser().getFirebaseUid(), review.getUser().getEmail(), review.getUser().getFullName()),
                review.getBusiness().getFirebaseUid(),
                review.getBusiness().getName(),
                review.getScore(),
                review.getDescription(),
                review.getReviewReply()
        );
    }

    @GetMapping("{reviewId}")
    ReviewResponse getReview(@PathVariable String reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        return new ReviewResponse(
                review.getId(),
                new PostOwner(review.getUser().getFirebaseUid(), review.getUser().getEmail(), review.getUser().getFullName()),
                review.getBusiness().getFirebaseUid(),
                review.getBusiness().getName(),
                review.getScore(),
                review.getDescription(),
                review.getReviewReply()
        );
    }

    @GetMapping
    ReviewResponse getReview(@RequestParam String userId, @RequestParam String businessId) {
        Review review = reviewService.getReviewByUserAndBusiness(userId, businessId);
        if (review == null) {
            throw new NotFoundException("Review doesn't exist");
        }
        return new ReviewResponse(
                review.getId(),
                new PostOwner(review.getUser().getFirebaseUid(), review.getUser().getEmail(), review.getUser().getFullName()),
                review.getBusiness().getFirebaseUid(),
                review.getBusiness().getName(),
                review.getScore(),
                review.getDescription(),
                review.getReviewReply()
        );
    }

    @DeleteMapping("{reviewId}")
    void deleteReview(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long reviewId) throws FirebaseAuthException {
        reviewService.deleteReview(Authentication.extractUid(authorizationHeader), reviewId);
    }

}
