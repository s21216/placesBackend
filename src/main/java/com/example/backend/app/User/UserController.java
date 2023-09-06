package com.example.backend.app.User;

import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    ResponseEntity<UserResponse> createUser(@RequestHeader("Authorization") String authorizationHeader,
                                            @RequestBody UserRequest userRequest) throws FirebaseAuthException {
        String firebaseToken = authorizationHeader.replace("Bearer ", "");
        User user = userService.createUser(userRequest.email(), userRequest.username(), userRequest.fullName(), firebaseToken);
        return new ResponseEntity<>(new UserResponse(
                user.getFirebaseUid(),
                user.getEmail(),
                user.getUsername(),
                user.getFullName(),
                user.getProfilePictureUrl(),
                user.getJoinDate()), HttpStatus.CREATED
        );
    }
}
