package com.example.backend.helpers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

public class Authentication {
    public static String extractToken(String authorizationHeader) throws FirebaseAuthException {
        String firebaseToken = authorizationHeader.replace("Bearer ", "");
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseToken);
        return decodedToken.getUid();
    }
}
