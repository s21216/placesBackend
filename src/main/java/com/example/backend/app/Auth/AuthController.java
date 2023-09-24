package com.example.backend.app.Auth;

import com.example.backend.app.Business.Business;
import com.example.backend.app.Business.DTO.BusinessRequest;
import com.example.backend.app.Business.BusinessService;
import com.example.backend.app.User.User;
import com.example.backend.app.User.DTO.UserRequest;
import com.example.backend.app.User.UserService;
import com.example.backend.exceptions.AccountNotFoundException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final UserService userService;

    private final BusinessService businessService;

    @PostMapping("signin")
    AuthResponse signIn(@RequestHeader("Authorization") String authorizationHeader) throws FirebaseAuthException {
        String firebaseToken = authorizationHeader.replace("Bearer ", "");
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseToken);
        User user = userService.findByFirebaseUid(decodedToken.getUid());
        if (user == null) {
            Business business = businessService.findByFirebaseUid(decodedToken.getUid());
            if (business == null) {
                throw new AccountNotFoundException("Account not found");
            }
            return new AuthResponse(business.getEmail(), business.getFirebaseUid(), Role.BUSINESS);
        }
        return new AuthResponse(user.getEmail(), user.getFirebaseUid(), Role.USER);
    }

    @PostMapping("/users/signup")
    AuthResponse createUser(@RequestHeader("Authorization") String authorizationHeader,
                            @RequestBody UserRequest userRequest) throws FirebaseAuthException {
        String firebaseToken = authorizationHeader.replace("Bearer ", "");
        User user = userService.createUser(userRequest.email(), userRequest.username(), userRequest.fullName(), firebaseToken);
        return new AuthResponse(user.getEmail(), user.getFirebaseUid(), Role.USER);
    }

    @PostMapping("/businesses/signup")
    AuthResponse createBusiness(@RequestHeader("Authorization") String authorizationHeader,
                                @RequestBody BusinessRequest businessRequest) throws FirebaseAuthException {
        String firebaseToken = authorizationHeader.replace("Bearer ", "");
        Business business = businessService.createBusiness(
                businessRequest.email(),
                businessRequest.name(),
                businessRequest.phoneNumber(),
                firebaseToken
        );
        return new AuthResponse(business.getEmail(), business.getFirebaseUid(), Role.USER);
    }
}
