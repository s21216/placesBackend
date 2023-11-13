package com.example.backend.app.CheckIn.DTO;

public record CheckInRequest (
        String userId,
        String businessId,
        Double latitude,
        Double longitude
){
}
