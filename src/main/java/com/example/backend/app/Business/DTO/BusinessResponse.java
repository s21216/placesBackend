package com.example.backend.app.Business.DTO;

import com.example.backend.app.Auth.Role;
import com.example.backend.app.Category.Category;
import com.example.backend.app.Business.Cost;

import java.time.LocalDate;
import java.util.Set;

public record BusinessResponse(
        String firebaseUid,
        String name,
        String email,
        String phoneNumber,
        String profilePictureUrl,
        String description,
        String type,
        Set<Category> categories,
        Cost cost,
        Double score,
        Location location,
        LocalDate joinDate,
        Role role) {
}
