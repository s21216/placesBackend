package com.example.backend.app.Review;

import com.example.backend.app.Review.DTO.CreateReviewRequest;
import com.example.backend.app.Review.DTO.PostOwner;
import com.example.backend.app.Review.DTO.ReviewResponse;
import com.example.backend.app.ReviewReply.DTO.ReviewReplyRequest;
import com.example.backend.app.ReviewReply.ReviewReply;
import com.example.backend.app.ReviewReply.ReviewReplyService;
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
    private final ReviewReplyService reviewReplyService;

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
    ReviewResponse getReview(@PathVariable Long reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        return new ReviewResponse(
                review.getId(),
                new PostOwner(
                        review.getUser().getFirebaseUid(),
                        review.getUser().getEmail(),
                        review.getUser().getFullName()
                ),
                review.getBusiness().getFirebaseUid(),
                review.getBusiness().getName(),
                review.getScore(),
                review.getDescription(),
                review.getReviewReply()
        );
    }

    @GetMapping
    ReviewResponse getReviewByUserAndBusiness(@RequestParam String userId, @RequestParam String businessId) {
        Review review = reviewService.getReviewByUserAndBusiness(userId, businessId);
        if (review == null) {
            throw new NotFoundException("Review doesn't exist");
        }
        return new ReviewResponse(
                review.getId(),
                new PostOwner(
                        review.getUser().getFirebaseUid(),
                        review.getUser().getEmail(),
                        review.getUser().getFullName()
                ),
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

    @GetMapping("{reviewId}/reply")
    ReviewReply getReviewReply(@PathVariable Long reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        return review.getReviewReply();
    }

    @PutMapping("{reviewId}/reply")
    ReviewReply createOrUpdateReviewReply(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long reviewId, @RequestBody ReviewReplyRequest request) throws FirebaseAuthException {
        return reviewReplyService.createOrUpdateReviewReply(Authentication.extractUid(authorizationHeader), reviewId, request.description());
    }

    @DeleteMapping("{reviewId}/reply")
    void deleteReviewReply(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long reviewId) throws FirebaseAuthException {
        reviewReplyService.removeReviewReply(Authentication.extractUid(authorizationHeader), reviewId);
    }
}
