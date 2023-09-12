package com.example.backend.app.Business;

import com.example.backend.app.Auth.Role;

import java.time.LocalDate;

public record BusinessResponse(String firebaseUid,
                           String email,
                           String phoneNumber,
                           String name,
                           String profilePictureUrl,
                           LocalDate joinDate,
                           Role role) {
}
