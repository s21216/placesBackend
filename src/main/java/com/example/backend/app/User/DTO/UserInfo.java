package com.example.backend.app.User.DTO;

public record UserInfo(
        String username,
        Integer numberOfCheckIns,
        Integer numberOfReviews) {
}
