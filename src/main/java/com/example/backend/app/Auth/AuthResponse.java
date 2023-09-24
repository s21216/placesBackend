package com.example.backend.app.Auth;

public record AuthResponse(String email, String firebaseUid, Role role){
}
