package com.example.backend.app.Auth.DTO;

import com.example.backend.app.Business.DTO.Location;

public record BusinessSignUpRequest(
        String email,
        String name,
        String phoneNumber,
        String type,
        Location location
) {
}
