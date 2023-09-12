package com.example.backend.app.Business;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    Optional<Business> findBusinessByFirebaseUid(String firebaseUid);
}
