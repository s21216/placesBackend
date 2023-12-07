package com.example.backend.app.User.DTO;

import com.example.backend.app.Business.Cost;

public record VisitedBusiness(
        String name,
        String firebaseUid,
        String address,
        String type,
        Cost cost,
        Double score
) {
}
