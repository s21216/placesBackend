package com.example.backend.app.User;

import com.example.backend.app.CheckIn.CheckIn;
import com.example.backend.app.CheckIn.CheckInService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CheckInService checkInService;

    @GetMapping("{userId}/checkIns")
    List<CheckIn> getVisited(@PathVariable String userId) {
        return checkInService.getVisitedByUser(userId);
    }

    @GetMapping("{userId}/checkIns/{businessId}")
    CheckIn checkInState(@PathVariable String userId, @PathVariable String businessId) {
        return checkInService.getCheckInState(userId, businessId);
    }

}
