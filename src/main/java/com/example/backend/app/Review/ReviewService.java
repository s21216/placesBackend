package com.example.backend.app.Review;

import com.example.backend.app.Business.Business;
import com.example.backend.app.Business.BusinessRepository;
import com.example.backend.app.Review.DTO.UpdateReviewRequest;
import com.example.backend.app.User.User;
import com.example.backend.app.User.UserRepository;
import com.example.backend.exceptions.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;

    public Review createReview(String userId, String businessId, Integer rating, String description) {
        User user = userRepository.findUserByFirebaseUid(userId).orElseThrow();
        Business business = businessRepository.findBusinessByFirebaseUid(businessId).orElseThrow();
        Review review = new Review(user, business, rating, description);
        return reviewRepository.save(review);
    }

    public Review getReviewById(String reviewId) {
        return reviewRepository.findById(Long.valueOf(reviewId)).orElse(null);
    }

    public List<Review> getReviewsByUserId(String userId) {
        return reviewRepository.findByUser_FirebaseUid(userId);
    }

    public Page<Review> getReviewsByBusinessId(String businessId, Pageable pageable) {
        return reviewRepository.findByBusiness_FirebaseUid(businessId, pageable);
    }

    public Review updateReview(String userId, String reviewId, UpdateReviewRequest request) {
        Review review = reviewRepository.findById(Long.valueOf(reviewId)).orElseThrow();
        if (!Objects.equals(userId, review.getUser().getFirebaseUid())) {
            throw new UnauthorizedException("Unauthorized");
        }
        review.setRating(request.rating());
        review.setDescription(request.description());
        return reviewRepository.save(review);
    }

    public void deleteReview(String userId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow();
        if (!Objects.equals(userId, review.getUser().getFirebaseUid())) {
            throw new UnauthorizedException("Unauthorized");
        }
        reviewRepository.deleteById(reviewId);
    }

}
