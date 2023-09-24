package com.example.backend.app.User.DTO;

import com.example.backend.app.Auth.Role;

import java.time.LocalDate;

public record UserResponse(String firebaseUid,
                           String email,
                           String username,
                           String fullName,
                           String profilePictureUrl,
                           LocalDate joinDate,
                           Role role) {
}
