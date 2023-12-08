package com.example.backend.app.Auth;

import com.example.backend.app.Auth.DTO.AuthResponse;
import com.example.backend.app.Auth.DTO.ChangeEmailRequest;
import com.example.backend.app.Business.Business;
import com.example.backend.app.Auth.DTO.BusinessSignUpRequest;
import com.example.backend.app.Business.BusinessService;
import com.example.backend.app.User.User;
import com.example.backend.app.Auth.DTO.UserSignUpRequest;
import com.example.backend.app.User.UserRepository;
import com.example.backend.app.User.UserService;
import com.example.backend.exceptions.AccountNotFoundException;
import com.example.backend.helpers.Authentication;
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
                            @RequestBody UserSignUpRequest userSignUpRequest) throws FirebaseAuthException {
        String firebaseToken = authorizationHeader.replace("Bearer ", "");
        User user = userService.createUser(userSignUpRequest.email(), userSignUpRequest.username(), userSignUpRequest.fullName(), firebaseToken);
        return new AuthResponse(user.getEmail(), user.getFirebaseUid(), Role.USER);
    }

    @PostMapping("/businesses/signup")
    AuthResponse createBusiness(@RequestHeader("Authorization") String authorizationHeader,
                                @RequestBody BusinessSignUpRequest businessSignUpRequest) throws FirebaseAuthException {
        String firebaseToken = authorizationHeader.replace("Bearer ", "");
        Business business = businessService.createBusiness(
                businessSignUpRequest.email(),
                businessSignUpRequest.name(),
                businessSignUpRequest.type(),
                businessSignUpRequest.phoneNumber(),
                businessSignUpRequest.location(),
                firebaseToken
        );
        return new AuthResponse(business.getEmail(), business.getFirebaseUid(), Role.BUSINESS);
    }

    @PostMapping("business/email")
    AuthResponse updateBusinessPassword(@RequestHeader("Authorization") String authorizaionHeader,
                                @RequestBody ChangeEmailRequest request) throws FirebaseAuthException {
        Business business = businessService.changeEmail(Authentication.extractUid(authorizaionHeader), request.email());
        return new AuthResponse(business.getEmail(), business.getFirebaseUid(), Role.BUSINESS);
    }

    @PostMapping("user/email")
    AuthResponse updateUserPassword(@RequestHeader("Authorization") String authorizaionHeader,
                                @RequestBody ChangeEmailRequest request) throws FirebaseAuthException {
        User user = userService.changeEmail(Authentication.extractUid(authorizaionHeader), request.email());
        return new AuthResponse(user.getEmail(), user.getFirebaseUid(), Role.USER);
    }
}
