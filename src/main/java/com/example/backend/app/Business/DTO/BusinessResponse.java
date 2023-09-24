package com.example.backend.app.Business.DTO;

import com.example.backend.app.Auth.Role;
import com.example.backend.app.Business.Cost;

import java.time.LocalDate;

public record BusinessResponse(
        String firebaseUid,
        String name,
        String email,
        String phoneNumber,
        String profilePictureUrl,
        String description,
        String cuisine,
        Cost cost,
        Double score,
        Location location,
        LocalDate joinDate,
        Role role) {
}
