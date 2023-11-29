package com.example.backend.app.Business;

import com.example.backend.app.Auth.Role;
import com.example.backend.app.Business.DTO.*;
import com.example.backend.app.Review.DTO.PostOwner;
import com.example.backend.app.Review.DTO.ReviewResponse;
import com.example.backend.helpers.Authentication;
import com.example.backend.helpers.PaginatedRequest;
import com.example.backend.helpers.PaginatedResponse;
import com.example.backend.app.Review.Review;
import com.example.backend.app.Review.ReviewService;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/businesses")
@RequiredArgsConstructor
public class BusinessController {

    private final BusinessService businessService;
    private final ReviewService reviewService;

    @GetMapping("/{businessId}")
    BusinessDetailsResponse getBusinessById(@PathVariable String businessId) {
        Business business = businessService.findByFirebaseUid(businessId);
        return new BusinessDetailsResponse(
                business.getFirebaseUid(),
                business.getName(),
                business.getEmail(),
                business.getPhoneNumber(),
                business.getProfilePictureUrl(),
                business.getDescription(),
                business.getType(),
                business.getCategories(),
                business.getCost(),
                business.getScore(),
                new Location(
                        business.getCountry(),
                        business.getAddress(),
                        business.getZipCode(),
                        business.getCity(),
                        business.getDistrict(),
                        business.getLocationLongitude(),
                        business.getLocationLatitude()
                ),
                business.getAttributes(),
                business.getJoinDate(),
                Role.BUSINESS
        );
    }

    @PostMapping("{businessId}/reviews")
    PaginatedResponse<ReviewResponse> getReviews(@PathVariable String businessId, @RequestBody PaginatedRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
        Page<Review> reviewPage = reviewService.getReviewsByBusinessId(businessId, pageable);
        return new PaginatedResponse<>(
                reviewPage.getSize(),
                reviewPage.getNumber(),
                reviewPage.getNumberOfElements(),
                reviewPage.getTotalElements(),
                reviewPage.getTotalPages(),
                reviewPage.getContent().stream()
                        .map(review -> new ReviewResponse(
                        review.getId(),
                        new PostOwner(review.getUser().getFirebaseUid(), review.getUser().getEmail(), review.getUser().getFullName()),
                        review.getBusiness().getFirebaseUid(),
                        review.getBusiness().getName(),
                        review.getScore(),
                        review.getDescription(),
                        review.getReviewReply()
                )).toList()
        );
    }

    @PutMapping("/details")
    void updateBusinessDetails(@RequestHeader("Authorization") String authorizationHeader, @RequestBody UpdateBusinessDetailsRequest request) throws FirebaseAuthException {
        String businessId = Authentication.extractUid(authorizationHeader);
        businessService.updateBusinessDetails(businessId, request);
    }

    @GetMapping("/search/autocomplete")
    List<String> autocomplete(@RequestParam String searchQuery) {
        return businessService.autocomplete(searchQuery);
    }

    @PostMapping("/search")
    PaginatedResponse<BusinessResponse> searchBusinessesFuzzy(@RequestParam String searchQuery, @RequestBody SearchRequest request) {
        List<Business> businesses = businessService.searchFuzzy(searchQuery, request);
        List<BusinessResponse> businessList = businesses.stream().map(business -> new BusinessResponse(
                        business.getFirebaseUid(),
                        business.getName(),
                        business.getEmail(),
                        business.getPhoneNumber(),
                        business.getProfilePictureUrl(),
                        business.getDescription(),
                        business.getType(),
                        business.getCategories(),
                        business.getCost(),
                        business.getScore(),
                        new Location(
                                business.getCountry(),
                                business.getAddress(),
                                business.getZipCode(),
                                business.getCity(),
                                business.getDistrict(),
                                business.getLocationLatitude(),
                                business.getLocationLongitude()
                        ),
                        business.getJoinDate(),
                        Role.BUSINESS
                )
        ).toList();

        return new PaginatedResponse<>(
                request.getPageSize(),
                request.getPageNumber(),
                businessList.size(),
                null,
                null,
                businessList);
    }

    @GetMapping("/reindex")
    void reindex() throws InterruptedException {
        businessService.reindex();
    }

}
