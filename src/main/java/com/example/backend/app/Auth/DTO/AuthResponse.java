package com.example.backend.app.Auth.DTO;

import com.example.backend.app.Auth.Role;

public record AuthResponse(String email, String firebaseUid, Role role){
}
