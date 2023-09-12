package com.example.backend.app.Business;

import com.example.backend.app.User.User;
import com.example.backend.app.User.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessService {

    private final BusinessRepository businessRepository;

    public Business createBusiness(String email, String name, String phoneNumber, String firebaseToken) throws FirebaseAuthException {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseToken);
        Business business = new Business(email, name, phoneNumber, decodedToken.getUid());
        businessRepository.save(business);
        return business;
    }

    public Business findByFirebaseToken(String firebaseToken) throws FirebaseAuthException {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseToken);
        return businessRepository.findBusinessByFirebaseUid(decodedToken.getUid()).orElse(null);
    }
}
