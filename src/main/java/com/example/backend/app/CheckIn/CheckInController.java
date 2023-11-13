package com.example.backend.app.CheckIn;

import com.example.backend.app.CheckIn.DTO.CheckInRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkIns")
@RequiredArgsConstructor
public class CheckInController {
    private final CheckInService checkInService;

    @PostMapping
    CheckIn createCheckIn(@RequestBody CheckInRequest request) throws FirebaseAuthException {
        return checkInService.createCheckIn(request.userId(), request.businessId(), request.latitude(), request.longitude());
    }

}
