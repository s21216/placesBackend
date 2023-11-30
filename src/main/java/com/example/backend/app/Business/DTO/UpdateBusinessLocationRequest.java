package com.example.backend.app.Business.DTO;

public record UpdateBusinessLocationRequest(
        String address,
        Double latitude,
        Double longitude
) {
}
