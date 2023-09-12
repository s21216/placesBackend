package com.example.backend.app.Auth;

public record LogInResponse (String email, String firebaseUid, Role role){
}
