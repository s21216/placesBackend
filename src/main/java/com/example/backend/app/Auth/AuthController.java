package com.example.backend.app.Auth;

import com.example.backend.app.Business.Business;
import com.example.backend.app.Business.BusinessRequest;
import com.example.backend.app.Business.BusinessResponse;
import com.example.backend.app.Business.BusinessService;
import com.example.backend.app.User.User;
import com.example.backend.app.User.UserRequest;
import com.example.backend.app.User.UserResponse;
import com.example.backend.app.User.UserService;
import com.example.backend.exceptions.AccountNotFoundException;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final UserService userService;

    private final BusinessService businessService;

    @PostMapping("signin")
    LogInResponse signIn(@RequestHeader("Authorization") String authorizationHeader) throws FirebaseAuthException {
        String firebaseToken = authorizationHeader.replace("Bearer ", "");
        User user = userService.findByFirebaseToken(firebaseToken);
        if (user == null) {
            Business business = businessService.findByFirebaseToken(firebaseToken);
            if (business == null) {
                throw new AccountNotFoundException("Account not found");
            }
            return new LogInResponse(business.getEmail(), business.getFirebaseUid(), Role.BUSINESS);
        }
        return new LogInResponse(user.getEmail(), user.getFirebaseUid(), Role.USER);
    }

    @PostMapping("/users/signup")
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
                user.getJoinDate(),
                Role.USER), HttpStatus.CREATED
        );
    }

    @PostMapping("/businesses/signup")
    ResponseEntity<BusinessResponse> createBusiness(@RequestHeader("Authorization") String authorizationHeader,
                                            @RequestBody BusinessRequest businessRequest) throws FirebaseAuthException {
        String firebaseToken = authorizationHeader.replace("Bearer ", "");
        Business business = businessService.createBusiness(
                businessRequest.email(),
                businessRequest.name(),
                businessRequest.phoneNumber(),
                firebaseToken
        );
        return new ResponseEntity<>(new BusinessResponse(
                business.getFirebaseUid(),
                business.getEmail(),
                business.getPhoneNumber(),
                business.getName(),
                business.getProfilePictureUrl(),
                business.getJoinDate(),
                Role.BUSINESS), HttpStatus.CREATED
        );
    }
}
