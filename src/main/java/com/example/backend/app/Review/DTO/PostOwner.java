package com.example.backend.app.Review.DTO;

public record PostOwner (
        String firebaseUid,
        String email,
        String fullName
){
}
