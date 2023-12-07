package com.example.backend.app.User;

import com.example.backend.app.Business.BusinessService;
import com.example.backend.app.CheckIn.CheckIn;
import com.example.backend.app.Review.Review;
import com.example.backend.app.Review.ReviewService;
import com.example.backend.app.User.DTO.UserInfo;
import com.example.backend.app.User.DTO.VisitedBusiness;
import com.example.backend.helpers.PaginatedRequest;
import com.example.backend.helpers.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ReviewService reviewService;

    @PostMapping("{userId}/visited")
    PaginatedResponse<VisitedBusiness> getVisitedBusinesses(@PathVariable String userId, @RequestBody PaginatedRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(), Sort.by("createdAt").descending());
        Page<CheckIn> checkInPage = userService.getVisitedByUser(userId, pageable);
        return new PaginatedResponse<>(
                checkInPage.getSize(),
                checkInPage.getNumber(),
                checkInPage.getNumberOfElements(),
                checkInPage.getTotalElements(),
                checkInPage.getTotalPages(),
                checkInPage.getContent().stream()
                        .map(CheckIn::getBusiness)
                        .map(business -> new VisitedBusiness(
                                business.getName(),
                                business.getFirebaseUid(),
                                business.getAddress(),
                                business.getType(),
                                business.getCost(),
                                business.getScore()
                        )).toList()
        );
    }

    @GetMapping("{userId}/reviews")
    List<Review> getReviews(@PathVariable String userId) {
        return reviewService.getReviewsByUserId(userId);
    }

    @GetMapping("{userId}")
    UserInfo getInfo(@PathVariable String userId) {
        User user = userService.findByFirebaseUid(userId);
        return new UserInfo(
                user.getUsername(),
                user.getCheckIns().size(),
                user.getReviews().size()
        );
    }
}
