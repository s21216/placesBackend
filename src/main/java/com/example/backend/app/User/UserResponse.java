package com.example.backend.app.User;

import java.time.LocalDate;

public record UserResponse(String firebaseUid,
                           String email,
                           String username,
                           String fullName,
                           String profilePictureUrl,
                           LocalDate joinDate) {
}
