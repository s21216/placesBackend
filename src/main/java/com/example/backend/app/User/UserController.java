package com.example.backend.app.User;

import com.example.backend.app.CheckIn.CheckIn;
import com.example.backend.app.CheckIn.CheckInService;
import com.example.backend.app.CheckIn.DTO.CheckInResponse;
import com.example.backend.app.Review.Review;
import com.example.backend.app.Review.ReviewRepository;
import com.example.backend.app.Review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CheckInService checkInService;
    private final ReviewService reviewService;

    @GetMapping("{userId}/checkIns")
    List<CheckInResponse> getVisited(@PathVariable String userId) {
        List<CheckIn> checkIns = checkInService.getVisitedByUser(userId);
        return checkIns.stream().map(checkIn -> new CheckInResponse(
                        checkIn.getNote(),
                        checkIn.getUser().getFirebaseUid(),
                        checkIn.getBusiness().getFirebaseUid(),
                        checkIn.getCreatedAt()
                )
        ).toList();
    }

    @GetMapping("{userId}/reviews")
    List<Review> getReviews(@PathVariable String userId) {
        return reviewService.getReviewsByUserId(userId);
    }

}
