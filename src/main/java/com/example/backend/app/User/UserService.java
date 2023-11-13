package com.example.backend.app.User;

import com.example.backend.app.CheckIn.CheckIn;
import com.example.backend.app.CheckIn.CheckInRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public User createUser(String email, String username, String fullName, String firebaseToken) throws FirebaseAuthException {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseToken);
        User user = new User(email, username, fullName, decodedToken.getUid());
        userRepository.save(user);
        return user;
    }

    public User findByFirebaseUid(String firebaseUid) throws FirebaseAuthException {
        return userRepository.findUserByFirebaseUid(firebaseUid).orElse(null);
    }
}
