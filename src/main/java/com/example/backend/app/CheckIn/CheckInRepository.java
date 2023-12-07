package com.example.backend.app.CheckIn;

import com.example.backend.app.Business.Business;
import com.example.backend.app.User.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    Page<CheckIn> findCheckInsByUser(User user, Pageable pageable);
    Optional<CheckIn> findCheckInByUserAndBusiness(User user, Business business);
}
