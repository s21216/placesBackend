package com.example.backend.app.Business.DTO;

public record Location(
        String country,
        String address,
        String zipCode,
        String city,
        String district,
        Double longitude,
        Double latitude) {
}
