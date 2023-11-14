package com.example.backend.app.CheckIn;

import com.example.backend.app.Business.Business;
import com.example.backend.app.Business.BusinessRepository;
import com.example.backend.app.User.User;
import com.example.backend.app.User.UserRepository;
import com.example.backend.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.engine.spatial.DistanceUnit;
import org.hibernate.search.engine.spatial.GeoPoint;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckInService {
    private final UserRepository userRepository;
    private final CheckInRepository checkInRepository;
    private final BusinessRepository businessRepository;

    private final EntityManager em;

    public CheckIn createCheckIn(String userId, String businessId, Double latitude, Double longitude) {
        SearchSession searchSession = Search.session(em);

        var business = searchSession.search(Business.class)
                .where((f, root) -> {
                            root.add(f.match().field("firebaseUid").matching(businessId));
                            root.add(f.spatial().within().field("location")
                                    .circle(GeoPoint.of(latitude, longitude), 200, DistanceUnit.METERS));
                        }
                ).fetchSingleHit();
        if (business.isEmpty()) {
            throw new NotFoundException("Too far from business");
        }
        User user = userRepository.findUserByFirebaseUid(userId).orElseThrow();
        CheckIn checkIn = new CheckIn(user, business.orElseThrow());
        return checkInRepository.save(checkIn);
    }

    public List<CheckIn> getVisitedByUser(String userId) {
        User user = userRepository.findUserByFirebaseUid(userId).orElseThrow();
        return checkInRepository.findCheckInsByUser(user);
    }

    public CheckIn getCheckInState(String userId, String businessId) {
        User user = userRepository.findUserByFirebaseUid(userId).orElseThrow();
        Business business = businessRepository.findBusinessByFirebaseUid(businessId).orElseThrow();
        return checkInRepository.findCheckInByUserAndBusiness(user, business).orElse(null);
    }

}
