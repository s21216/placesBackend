package com.example.backend.app.Review;

import com.example.backend.app.Review.DTO.CreateReviewRequest;
import com.example.backend.app.Review.DTO.UpdateReviewRequest;
import com.example.backend.helpers.Authentication;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    Review createReview(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CreateReviewRequest request) throws FirebaseAuthException {
        return reviewService.createReview(Authentication.extractToken(authorizationHeader), request.businessId(), request.rating(), request.description());
    }

    @GetMapping("{reviewId}")
    Review getReview(@PathVariable String reviewId) {
        return reviewService.getReviewById(reviewId);
    }

    @PutMapping
    Review updateReview(@RequestHeader("Authorization") String authorizationHeader, @RequestBody UpdateReviewRequest request) throws FirebaseAuthException {
        return reviewService.updateReview(Authentication.extractToken(authorizationHeader), request.reviewId(), request);
    }

    @DeleteMapping("{reviewId}")
    void deleteReview(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long reviewId) throws FirebaseAuthException {
        reviewService.deleteReview(Authentication.extractToken(authorizationHeader), reviewId);
    }

}
