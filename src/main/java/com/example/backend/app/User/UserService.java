package com.example.backend.app.User;

import com.example.backend.app.CheckIn.CheckIn;
import com.example.backend.app.CheckIn.CheckInRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CheckInRepository checkInRepository;
    public User createUser(String email, String username, String fullName, String firebaseToken) throws FirebaseAuthException {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseToken);
        User user = new User(email, username, fullName, decodedToken.getUid());
        userRepository.save(user);
        return user;
    }

    public User findByFirebaseUid(String firebaseUid) {
        return userRepository.findUserByFirebaseUid(firebaseUid).orElse(null);
    }

    public Page<CheckIn> getVisitedByUser(String userId, Pageable pageable) {
        User user = userRepository.findUserByFirebaseUid(userId).orElseThrow();
        return checkInRepository.findCheckInsByUser(user, pageable);
    }
}
