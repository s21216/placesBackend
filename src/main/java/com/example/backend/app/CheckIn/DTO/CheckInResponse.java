package com.example.backend.app.CheckIn.DTO;

import java.time.Instant;

public record CheckInResponse (
        String note,
        String userId,
        String businessId,
        Instant createdAt){
}
