package com.example.backend.app.CheckIn;

import com.example.backend.app.CheckIn.DTO.CheckInRequest;
import com.example.backend.app.CheckIn.DTO.CheckInResponse;
import com.example.backend.exceptions.NotFoundException;
import com.example.backend.helpers.Authentication;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkIns")
@RequiredArgsConstructor
public class CheckInController {
    private final CheckInService checkInService;

    @PostMapping
    CheckInResponse createCheckIn(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CheckInRequest request) throws FirebaseAuthException {
        CheckIn checkIn = checkInService.createCheckIn(Authentication.extractToken(authorizationHeader), request.businessId(), request.latitude(), request.longitude());
        return new CheckInResponse(
                checkIn.getNote(),
                checkIn.getUser().getFirebaseUid(),
                checkIn.getBusiness().getFirebaseUid(),
                checkIn.getCreatedAt()
        );
    }

    @GetMapping
    Boolean getCheckInExists(@RequestParam String userId, @RequestParam String businessId) {
        CheckIn checkIn = checkInService.getCheckInState(userId, businessId);
        if (checkIn == null) {
            return false;
        } else {
            return true;
        }
    };

}
